package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.entity.ProductAttributes;
import org.springframework.stereotype.Component;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.dto.ProductDto;

@Component
public class ProductDtoMapper {
    public ProductDto mapObject(Product entity) {
        return new ProductDto(
            entity.getId(),
            entity.getProductName(),
            entity.getProductAttributes().getColor(),
            entity.getProductAttributes().getSize(),
            entity.getSku()
        );
    }

    public Product mapEntity(ProductDto dto) {
        ProductAttributes attribute = new ProductAttributes(dto.getProductColor(), dto.getSize());
        return new Product(
            dto.getProductId(),
            dto.getSku(),
            dto.getProductName(),
            attribute
        );
    }
}