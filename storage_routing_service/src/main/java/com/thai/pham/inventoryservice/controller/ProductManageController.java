package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/manage")
public class ProductController {
    private final ProductService productService;
    private final PageDtoMapper mapper;

    @Autowired
    public ProductController(
        ProductService productService,
        PageDtoMapper mapper
    ) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDto<Product>> getProductFromName(@RequestParams("name") String searchTerm, Pageable pageable) {
        Page<Product> products = productService.findAllProductByName(searchTerm, pageable);
        PageDto<Product> productPageDtos = mapper.toPageDto(products);
        return ResponseEntity(productPageDtos, HttpStatus.OK);
    }
}