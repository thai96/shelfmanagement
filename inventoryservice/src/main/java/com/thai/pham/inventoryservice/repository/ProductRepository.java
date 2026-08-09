package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "  EXPLAIN ANALYZE SELECT p1_0.id," +
            "         p1_0.attributes," +
            "         p1_0.name," +
            "         p1_0.sku " +
            "     FROM product p1_0 ", nativeQuery = true)
    public Page<Product> findProductAll(Pageable pageable);
    public Page<Product> findProductByProductNameContaining(String searchTerm, Pageable pageable);
    public Product findProductById(UUID productId);
}