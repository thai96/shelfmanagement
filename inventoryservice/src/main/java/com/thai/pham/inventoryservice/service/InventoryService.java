package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.exception.InventoryException;
import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.keygenerator.InventoryKeyGenerator;
import com.thai.pham.inventoryservice.repository.specification.InventorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import com.thai.pham.inventoryservice.mapper.InventoryDtoMapper;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InventoryService {
    private static final Long SINGLE_ITEM_TTL_MILLIS = 10000L;

    private final InventoryRepository inventoryRepo;
    private final PageDtoMapper pageMapper;
    private final InventoryDtoMapper inventoryMapper;
    private final RedisService redisService;
    private final InventoryKeyGenerator inventoryKeyGenerator;
    private final InventorySpecification inventorySpecification;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepo, PageDtoMapper pageMapper,
                            InventoryDtoMapper inventoryMapper, RedisService redisService, InventoryKeyGenerator inventoryKeyGenerator, InventorySpecification inventorySpecification) {
        this.inventoryRepo = inventoryRepo;
        this.pageMapper = pageMapper;
        this.inventoryMapper = inventoryMapper;
        this.redisService = redisService;
        this.inventoryKeyGenerator = inventoryKeyGenerator;
        this.inventorySpecification = inventorySpecification;
    }

    private void saveSingleInventoryDtoCache(InventoryDto inventoryDto) {
        String key = inventoryKeyGenerator.generateSingleInventoryKey(inventoryDto);
        redisService.saveInventoryDto(key, inventoryDto, SINGLE_ITEM_TTL_MILLIS);
    }

    public PageDto<InventoryDto> getAllInventory(Pageable pageable) {
        Page<InventoryDto> inventoriesDto = inventoryRepo.findAll(pageable).map(inventoryMapper::mapObject);
        String pageKey = inventoryKeyGenerator.generatePageKey(pageable);
        List<UUID> listUUIDs = inventoriesDto.stream().peek(this::saveSingleInventoryDtoCache).map(InventoryDto::getId).toList();
        redisService.saveItemIds(pageKey, listUUIDs);
        return pageMapper.mapObject(inventoriesDto);
    }

    public PageDto<InventoryDto> getAllInventory(ListInventoryRequest request, Pageable pageable) {
        Specification<Inventory> inventorySpec = inventorySpecification.addFilterByProduct(request.productId())
                .and(inventorySpecification.addFilterByLocation(request.locationId()))
                .and(inventorySpecification.addFilterByProductSku(request.sku()));
        Page<InventoryDto> inventoriesDto = inventoryRepo.findAll(inventorySpec, pageable).map(inventoryMapper::mapObject);
        String pageKey = inventoryKeyGenerator.generatePageKey(pageable);
        List<UUID> listUUIDs = inventoriesDto.stream().peek(this::saveSingleInventoryDtoCache).map(InventoryDto::getId).toList();
        redisService.saveItemIds(pageKey, listUUIDs);
        return pageMapper.mapObject(inventoriesDto);
    }

    public ProductInventoryRecord checkAvailableSku(SkuAvailabilityRequest request) {
        return inventoryRepo.getProductInventoryRecord(request.sku(), request.locationId()).orElseThrow(InventoryException::skuNotFound);
    }

    public ProductInventoryRecordList batchCheckAvailableSku(BatchCheckSkuAvailabilityRequest request) {
        List<String> skus = new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        request.items().forEach((item) -> {
            skus.addLast(item.sku());
            ids.addLast(item.locationId());
        });
        return new ProductInventoryRecordList(inventoryRepo.getProductInventoryRecords(skus, ids));
    }

    @Transactional(readOnly = false, timeout = 3)
    public OfflineSaleDeductResult deductOfflineSale(InventoryDeductRequest request) {
        Map<String, Integer> aggregated = request.deductDetailItemRequests().stream().collect(Collectors.groupingBy(DeductDetailItemRequest::sku, Collectors.summingInt(DeductDetailItemRequest::qty)));
        List<Map.Entry<String, Integer>> sortedRequest = aggregated.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();
        Map<UUID, Integer> reducedMap = new HashMap<>();
        List<Inventory> changeInventory = sortedRequest.stream().map(item -> {
            String sku = item.getKey();
            Integer reduceQty = item.getValue();
            Inventory availableInventory = inventoryRepo.findInventoryByLocationAndSku(request.locationId(), sku, reduceQty)
                    .orElseThrow(() -> new InventoryException(ErrorCode.INSUFFICIENT_STOCK));
            availableInventory.setQtyOnHand(availableInventory.getQtyOnHand() - reduceQty);
            availableInventory.setQtyAvailable(availableInventory.getQtyAvailable() - reduceQty);
            reducedMap.put(availableInventory.getId(), reduceQty);
            return availableInventory;
        }).toList();
        inventoryRepo.saveAllAndFlush(changeInventory);
        List<OfflineDeductItemResult> offlineItemResult = inventoryRepo.findAllById(changeInventory.stream().map(Inventory::getId).toList())
                .stream().map(inv -> new OfflineDeductItemResult(inv.getProduct().getSku(), reducedMap.get(inv.getId()), inv.getQtyOnHand(), inv.getQtyAvailable()))
                .toList();
        return new OfflineSaleDeductResult(request.ref(), offlineItemResult);
    }

    @Transactional()
    public List<InventoryDto> updateInventory(List<InventoryChangeDto> dataChangeList, String requestId) {
        Map<UUID, InventoryChangeDto> changeLookUpMap = dataChangeList.stream().collect(Collectors.toMap(InventoryChangeDto::getItemId, Function.identity()));
        List<UUID> ids = dataChangeList.stream().map(InventoryChangeDto::getItemId).toList();
        List<Inventory> changeInventory = inventoryRepo.findInventoryByIdIn(ids).stream()
                .map(inventory ->
                        Optional.ofNullable(changeLookUpMap.get(inventory.getId()))
                                .map(changeData -> processInventoryChange(inventory, changeData.getChangeType(), changeData.getChangeQty())).orElse(inventory)
                ).toList();
        List<Inventory> updatingInventory = new LinkedList<>(changeInventory);
        return inventoryRepo.saveAll(updatingInventory).stream().map(inventoryMapper::mapObject)
                .peek(this::saveSingleInventoryDtoCache).toList();
    }

    private Inventory processInventoryChange(Inventory inventory, InventoryChangeType changeType, int changeQty) {
        return switch (changeType) {
            case PRODUCT_DELIVERY -> processDeliveryAction(inventory, changeQty);
            case PRODUCT_IMPORT -> processImportAction(inventory, changeQty);
            case PRODUCT_RESERVE -> processReserveAction(inventory, changeQty);
            default -> throw new RuntimeException("Un-defined action for change type " + changeType);
        };
    }

    private Inventory processImportAction(Inventory inventoryItem, int changeValue) {
        inventoryItem.setQtyOnHand(inventoryItem.getQtyOnHand() + changeValue);
        inventoryItem.setQtyAvailable(inventoryItem.getQtyAvailable() + changeValue);
        return inventoryItem;
    }

    private Inventory processDeliveryAction(Inventory inventoryItem, int changeValue) {
        if (!isValidOnHandQuantity(inventoryItem, changeValue)) {
            return inventoryItem;
        }
        inventoryItem.setQtyOnHand(inventoryItem.getQtyOnHand() - changeValue);
        inventoryItem.setQtyReserved(inventoryItem.getQtyReserved() - changeValue);
        return inventoryItem;
    }

    private Inventory processReserveAction(Inventory inventoryItem, int changeValue) {
        if (!isValidOnHandQuantity(inventoryItem, changeValue)) {
            return inventoryItem;
        }
        inventoryItem.setQtyAvailable(inventoryItem.getQtyAvailable() - changeValue);
        inventoryItem.setQtyReserved(inventoryItem.getQtyReserved() + changeValue);
        return inventoryItem;
    }

    private Boolean isValidOnHandQuantity(Inventory inventory, int requiredQuantity) {
        return inventory.getQtyAvailable() >= requiredQuantity && inventory.getQtyOnHand() >= requiredQuantity;
    }

    public Long findOnHandInventoryOfProduct(UUID productId) {
        return inventoryRepo.findOnHandInventoryByProduct(productId).orElse(0L);
    }
}