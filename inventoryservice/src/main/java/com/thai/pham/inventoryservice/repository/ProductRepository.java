package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    public Page<Product> findProductByProductNameContaining(String searchTerm, Pageable pageable);

    public Product findProductById(UUID productId);

    @Query("""
                 SELECT CASE WHEN COUNT(p.id) = :expected_output THEN 1
                     ELSE 0
                     END AS output
                 FROM Product p
                 WHERE p.id IN :products_id
            """)
    boolean checkProductsExisted(@Param("expected_output") Long expectedIdQuantity, @Param("products_id") List<UUID> productsId);
}