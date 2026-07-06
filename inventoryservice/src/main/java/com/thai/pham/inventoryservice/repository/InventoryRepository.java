package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Collection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    public List<Inventory> findInventoryByIdIn(Collection<UUID> ids);
    public List<Inventory> findInventoryByIdNotIn(Collection<UUID> ids);
}