package com.thai.pham.inventoryservice.repository.specification;

import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.entity.Product;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InventorySpecification {
    public Specification<Inventory> addFilterByLocation(UUID locationId) {
        return (root, query, criteriaBuilder) -> {
            Join<Inventory, Location> locationJoin = root.join("location");
            return locationId == null ? criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(root.get("id"), locationId);
        };
    }

    public Specification<Inventory> addFilterByProduct(UUID productId) {
        return (root, query, criteriaBuilder) -> {
            Join<Inventory, Product> productJoin = root.join("product");
            return productId == null ? criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(productJoin.get("id"), productId);
        };
    }

    public Specification<Inventory> addFilterByProductSku(String sku) {
        return (root, query, criteriaBuilder) -> {
            Join<Inventory, Product> productJoin = root.join("product");
            return sku == null || sku.isBlank() ? criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(productJoin.get("sku"), sku);
        };
    }
}
