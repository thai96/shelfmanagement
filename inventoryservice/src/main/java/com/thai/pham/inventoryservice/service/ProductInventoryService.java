package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.exception.ProductDeletionConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProductInventoryService {
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public ProductInventoryService(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        if (inventoryService.findOnHandInventoryOfProduct(productId) > 0) {
            throw new ProductDeletionConflictException();
        }
        productService.deleteProductById(productId);
    }
}
