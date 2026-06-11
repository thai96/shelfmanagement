package com.thai.pham.storageroutingservice.service;

import com.thai.pham.storageroutingservice.entity.Inventory;
import com.thai.pham.storageroutingservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepo;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepo.findAll();
    }
}