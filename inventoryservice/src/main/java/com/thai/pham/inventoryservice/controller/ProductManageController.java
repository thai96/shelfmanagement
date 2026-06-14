package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.ProductInventoryDetailDto;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products/")
public class ProductManageController {
    private final ProductService productService;
    private final PageDtoMapper mapper;

    @Autowired
    public ProductManageController(
            ProductService productService,
            PageDtoMapper mapper
    ) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<PageDto<Product>> getProductFromName(@RequestParam("name") String searchTerm, Pageable pageable) {
        Page<Product> products = productService.findAllProductByName(searchTerm, pageable);
        PageDto<Product> productPageDtos = mapper.toPageDto(products);
        return new ResponseEntity<>(productPageDtos, HttpStatus.OK);
    }

    @PostMapping("/detail/{id}")
    public ResponseEntity<ProductInventoryDetailDto> getProductDetail(@PathVariable(name = "id") UUID id) {
        ProductInventoryDetailDto productDetail = productService.findProductById(id);
        return ResponseEntity.ok(productDetail);
    }

    @PutMapping("/new")
    public ResponseEntity<ProductInventoryDetailDto> createNewProduct(
            @RequestBody List<ProductInventoryDetailDto> products
    ) {
        List<ProductInventoryDetailDto> createdResult = productService.createProducts(products);
        return ResponseEntity.ok(createdResult);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<>
}