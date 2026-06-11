package com.thai.pham.storageroutingservice.repository;

import com.thai.pham.storageroutingservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {}