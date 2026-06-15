package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    public Page<Product> findProductByProductNameContaining(String searchTerm, Pageable pageable);
    public Product findProductById(UUID productId);
}