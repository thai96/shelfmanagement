package com.thai.pham.inventoryservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.dto.ProductDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.dto.LocationDto;

@Component
public class InventoryDtoMapper {
    private final ProductDtoMapper productMapper;
    private final LocationDtoMapper locationMapper;

    @Autowired
    public InventoryDtoMapper(ProductDtoMapper productMapper, LocationDtoMapper locationMapper) {
        this.productMapper = productMapper;
        this.locationMapper = locationMapper;
    }

    public InventoryDto mapObject(Inventory entity) {
        LocationDto locationDto = locationMapper.mapObject(entity.getLocation());
        ProductDto productDto = productMapper.mapObject(entity.getProduct());
        return new InventoryDto(
            entity.getId(),
            locationDto,
            productDto,
            entity.getQtyAvailable(),
            entity.getQtyOnHand(),
            entity.getQtyReserved()
        );
    }

    public Inventory mapEntity(InventoryDto dto) {
        Location locationEntity = locationMapper.mapEntity(dto.getLocation());
        Product productEntity = productMapper.mapEntity(dto.getProduct());
        return new Inventory(
            dto.getId(),
            dto.getQtyOnHand(),
            dto.getQtyReserved(),
            dto.getQtyAvailable(),
            locationEntity,
            productEntity
        );
    }
}