package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Page<Product> getAllVariationOfProduct(String productName, Pageable pageable) {
        return productRepo.findProductByProductNameContaining(productName, pageable);
    }

    
}