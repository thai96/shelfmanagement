package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.dto.ProductInventoryRecord;
import com.thai.pham.inventoryservice.entity.Inventory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Collection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {
    List<Inventory> findInventoryByIdIn(Collection<UUID> ids);

    List<Inventory> findInventoryByIdNotIn(Collection<UUID> ids);

    @Query("SELECT SUM(i.qtyOnHand) FROM Inventory i WHERE i.product.id = :productId")
    Optional<Long> findOnHandInventoryByProduct(@Param(("productId")) UUID productId);

    @Query("SELECT CASE WHEN SUM(i.qtyOnHand) > 0 THEN true ELSE false END FROM Inventory i WHERE i.location.id = :locationId")
    Optional<Boolean> checkAvailableInventoryByLocation(@Param("locationId") UUID locationId);

    @Query("SELECT new com.thai.pham.inventoryservice.dto.ProductInventoryRecord(p.sku, i.location.id, i.qtyAvailable, i.updatedAt) FROM Inventory i JOIN i.product p JOIN i.location l WHERE p.sku = :sku AND i.location.id = :locationId")
    Optional<ProductInventoryRecord> getProductInventoryRecord(@Param("sku") String sku, @Param("locationId") UUID locationId);

    @Query("SELECT new com.thai.pham.inventoryservice.dto.ProductInventoryRecord(p.sku, i.location.id, i.qtyAvailable, i.updatedAt) FROM Inventory i JOIN i.product p JOIN i.location l WHERE p.sku IN :skus AND i.location.id IN :locationIds")
    List<ProductInventoryRecord> getProductInventoryRecords(@Param("skus") List<String> sku, @Param("locationIds") List<UUID> locationId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2")})
    @Query("SELECT i FROM Inventory i JOIN FETCH i.product p WHERE i.location.id = :location_id AND p.sku = :sku AND i.qtyOnHand >= :reduce_qty AND i.qtyAvailable >= :reduce_qty ORDER BY p.sku ASC")
    Optional<Inventory> findInventoryByLocationAndSku(@Param("location_id") UUID locationId, @Param("sku") String sku, @Param("reduce_qty") Integer reduceQty);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "-2")})
    @Query("SELECT i FROM Inventory i JOIN FETCH i.product p WHERE i.location.id = :location_id AND p.sku = :sku AND i.qtyAvailable >= :reserve_qty")
    Optional<Inventory> findInventoryForReserve(@Param("location_id") UUID locationId, @Param("sku") String sku, @Param("reserve_qty") Integer reserveQty);
}