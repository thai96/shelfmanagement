package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.ProductAttributeDto;
import com.thai.pham.inventoryservice.entity.ProductAttributes;
import org.springframework.stereotype.Component;

@Component
public class ProductAttributeMapper {
    public ProductAttributes mapEntity(ProductAttributeDto dto) {
        return new ProductAttributes(dto.getColor(), dto.getSize());
    }

    public ProductAttributeDto mapObject(ProductAttributes entity) {
        return new ProductAttributeDto(entity.getColor(), entity.getSize());
    }
}
