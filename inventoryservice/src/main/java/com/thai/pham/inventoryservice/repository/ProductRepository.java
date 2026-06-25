package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    public Page<Product> findProductByProductNameContaining(String searchTerm, Pageable pageable);
    public Product findProductById(UUID productId);
}