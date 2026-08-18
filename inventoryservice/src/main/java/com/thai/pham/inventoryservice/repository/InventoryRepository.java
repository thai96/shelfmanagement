package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Collection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findInventoryByIdIn(Collection<UUID> ids);

    List<Inventory> findInventoryByIdNotIn(Collection<UUID> ids);

    @Query("SELECT SUM(i.qtyOnHand) FROM Inventory i WHERE i.product.id = :productId")
    Optional<Long> findOnHandInventoryByProduct(@Param(("productId")) UUID productId);

    @Query("SELECT CASE WHEN SUM(i.qtyOnHand) > 0 THEN true ELSE false END FROM Inventory i WHERE i.location.id = :locationId")
    Optional<Boolean> checkAvailableInventoryByLocation(@Param("locationId") UUID locationId);
}