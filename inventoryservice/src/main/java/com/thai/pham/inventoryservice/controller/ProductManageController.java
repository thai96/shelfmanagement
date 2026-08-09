package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.service.ProductService;

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
    public ResponseEntity<PageDto<ProductResult>> getProductFromName(@RequestParam(value = "search", required = false, defaultValue = "") String searchTerm, Pageable pageable) {
        Page<ProductResult> products = productService.findAllProductByName(searchTerm, pageable);
        PageDto<ProductResult> productPageDto = mapper.mapObject(products);
        return ResponseEntity.ok(productPageDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResult> getProductDetail(@PathVariable(name = "id") UUID id) {
        ProductResult productDetail = productService.findProductById(id);
        return ResponseEntity.ok(productDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResult> createNewProduct(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequest products
    ) {
        ProductResult createdResult = productService.updateProduct(id, products);
        return createdResult != null ? ResponseEntity.ok(createdResult) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID productId) {
        if(productService.deleteProductById(productId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/update")
//    public ResponseEntity<ProductUpdateDto> updateProduct(
//        @RequestBody ProductUpdateDto productUpdateDto
//    ) {
//        ProductUpdateDto updatedData = productService.updateOrInsertProduct(productUpdateDto);
//        return ResponseEntity.ok(updatedData);
//    }

    @PostMapping
    public ResponseEntity<ProductResult> createProduct(CreateProductRequest createProductRequest) {
        ResourceCreatedResult<ProductResult> createdResult = productService.createProducts(createProductRequest);
        return ResponseEntity.created(createdResult.getResourceUri()).body(createdResult.getResponseBodyContent());
    }
}