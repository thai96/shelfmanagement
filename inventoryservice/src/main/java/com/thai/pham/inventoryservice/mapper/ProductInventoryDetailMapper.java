package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationDto;
import com.thai.pham.inventoryservice.dto.ProductInventoryDetailDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductInventoryDetailMapper {
    private final LocationDtoMapper locationMapper;

    @Autowired
    public ProductInventoryDetailMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }


    public ProductInventoryDetailDto mapObject(Product product) {
        Boolean isProductAvailable = Optional.ofNullable(product.getInventory()).map(item -> item.stream()
                .mapToInt(this::findAvailableQuantity).sum() > 0).orElse(null);
        List<LocationDto> locationDtos = Optional.ofNullable(product.getInventory()).map(item -> item.stream()
                .map(inventory -> locationMapper.mapObject(inventory.getLocation()))
                .toList()).orElse(new ArrayList<>());

        return new ProductInventoryDetailDto(
            product.getId(),
            product.getProductName(),
            isProductAvailable,
            product.getProductAttributes().getColor(),
            product.getProductAttributes().getSize(),
            product.getSku(),
            locationDtos
        );
    }

    private Integer findAvailableQuantity(Inventory inventory) {
        if(inventory.getQtyAvailable() != null) {
            return inventory.getQtyAvailable();
        }

        if(inventory.getQtyOnHand() != null && inventory.getQtyReserved() != null) {
            return inventory.getQtyOnHand() - inventory.getQtyReserved();
        }

        return 0;
    }
}