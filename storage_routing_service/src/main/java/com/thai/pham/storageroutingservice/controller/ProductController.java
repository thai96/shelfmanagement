package com.thai.pham.storageroutingservice.controller;

import com.thai.pham.storageroutingservice.entity.Product;
import com.thai.pham.storageroutingservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }
}