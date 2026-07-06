package com.thai.pham.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.dto.InventoryChangeDto;
import com.thai.pham.inventoryservice.dto.InventoryChangeType;
import com.thai.pham.inventoryservice.mapper.InventoryDtoMapper;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final PageDtoMapper pageMapper;
    private final InventoryDtoMapper inventoryMapper;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepo,PageDtoMapper pageMapper,
        InventoryDtoMapper inventoryMapper) {
        this.inventoryRepo = inventoryRepo;
        this.pageMapper = pageMapper;
        this.inventoryMapper = inventoryMapper;
    }


    public PageDto<InventoryDto> getAllInventory(Pageable pageable) {
        Page<InventoryDto> inventoriesDto = inventoryRepo.findAll(pageable).map(inventoryMapper::mapObject);
        return pageMapper.mapObject(inventoriesDto);
    }

    @Transactional(readOnly = false)
    public List<InventoryDto> updateInventory(List<InventoryChangeDto> dataChangeList, String requestId) {
        Map<UUID, InventoryChangeDto> changeLookUpMap = dataChangeList.stream().collect(Collectors.toMap(InventoryChangeDto::getItemId, Function.identity()));
        List<UUID> ids = dataChangeList.stream().map(InventoryChangeDto::getItemId).toList();
        List<Inventory> changeInventory = inventoryRepo.findInventoryByIdIn(ids).stream()
                                            .map(inventory -> 
                                                Optional.ofNullable(changeLookUpMap.get(inventory.getId()))
                                                .map(changeData -> processInventoryChange(inventory, changeData.getChangeType(), changeData.getChangeQty())).orElse(inventory)
                                            ).toList();
        List<Inventory> updatingInventory = new LinkedList<>(changeInventory);
        return inventoryRepo.saveAll(updatingInventory).stream().map(inventoryMapper::mapObject).toList();
    }

    private Inventory processInventoryChange(Inventory inventory, InventoryChangeType changeType, int changeQty) {
        return switch(changeType) {
            case PRODUCT_DELIVERY -> processDeliveryAction(inventory, changeQty);
            case PRODUCT_IMPORT -> processImportAction(inventory, changeQty);
            case PRODUCT_RESERVE -> processReserveAction(inventory, changeQty);
            default -> throw new RuntimeException("Un-defined action for change type " + changeType );
        };
    }

    private Inventory processImportAction(Inventory inventoryItem, int changeValue) {
        inventoryItem.setQtyOnHand(inventoryItem.getQtyOnHand() + changeValue);
        inventoryItem.setQtyAvailable(inventoryItem.getQtyAvailable() + changeValue);
        return inventoryItem;
    }

    private Inventory processDeliveryAction(Inventory inventoryItem, int changeValue) {
        inventoryItem.setQtyOnHand(inventoryItem.getQtyOnHand() - changeValue);
        inventoryItem.setQtyReserved(inventoryItem.getQtyReserved() - changeValue);
        return inventoryItem;
    }

    private Inventory processReserveAction(Inventory inventoryItem, int changeValue) {
        inventoryItem.setQtyAvailable(inventoryItem.getQtyAvailable() - changeValue);
        inventoryItem.setQtyReserved(inventoryItem.getQtyReserved() + changeValue);
        return inventoryItem;
    }
}