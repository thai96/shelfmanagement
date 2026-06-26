package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Collection;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    public List<Inventory> findInventoryByIdIn(Collection<UUID> ids);
}