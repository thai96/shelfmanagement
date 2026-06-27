package com.thai.pham.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.dto.InventoryChangeDto;

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
        Map<UUID, InventoryChangeDto> changeLookUpMap = dataChangeList.stream().collect(Collectors.toMap(InventoryChangeDto::getId, Function.identity()));
        List<UUID> ids = dataChangeList.stream().map(info -> info.getItemId()).toList();
        List<Inventory> changeInventory = inventoryRepo.findInventoryByIdIn(ids)
                                            .map(inventory -> 
                                                Optional.ofNullable(changeLookUpMap.get(inventory.getId()))
                                                .map(changeData -> processInventoryChange(inventory, changeData.getChangeType(), changeData.getChangeQty())).orElse(inventory)
                                            ).toList();
        Set<UUID> changeSet = changeInventory.stream().map(item -> item.getId()).collect(Collectors.toSet());
        List<Inventory> newInventory = dataChangeList.stream().map(inventoryMapper::mapEntity)
                                    .filter(item -> !changeSet.contains(item.getId()))
                                    .map(item -> {
                                        item.setId(null);
                                        return item;
                                    }).toList();
        List<Inventory> updatingInventory = new LinkedList();
        updatingInventory.addAll(changeInventory);
        updatingInventory.addAll(newInventory);
        return inventoryRepo.saveAll(updatingInventory).map(inventoryMapper::mapObject).toList();
    }

    private Inventory processInventoryChange(Inventory inventory, InventoryChangeType changeType, int changeQty) {
        switch(changeType) {
            InventoryChangeType.PRODUCT_DELIVERY -> return processDeliveryAction(inventory, changeQty);
            InventoryChangeType.PRODUCT_IMPORT -> return processImportAction(inventory, changeQty);
            InventoryChangeType.PRODUCT_RESERVE -> return processReserveAction(inventory, changeQty);
            default -> throw new RuntimeException("Un-defined action for change type " + changeType );
        }
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