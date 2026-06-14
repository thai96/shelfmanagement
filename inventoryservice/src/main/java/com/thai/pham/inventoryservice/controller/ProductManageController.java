package com.thai.pham.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.service.ProductService;

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


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDto<Product>> getProductFromName(@RequestParam("name") String searchTerm, Pageable pageable) {
        Page<Product> products = productService.findAllProductByName(searchTerm, pageable);
        PageDto<Product> productPageDtos = mapper.toPageDto(products);
        return new ResponseEntity<>(productPageDtos, HttpStatus.OK);
    }
}