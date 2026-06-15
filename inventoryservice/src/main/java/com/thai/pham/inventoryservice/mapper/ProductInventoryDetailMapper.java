package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationDto;
import com.thai.pham.inventoryservice.dto.ProductInventoryDetailDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInventoryDetailMapper {
    private LocationDtoMapper locationMapper;

    @Autowired
    public ProductInventoryDetailMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }


    public ProductInventoryDetailDto mapObject(Product product) {
        Integer productAvailable = product.getInventory().stream()
            .mapToInt(this::findAvailableQuantity).sum();
        Boolean isProductAvailable = productAvailable > 0;
        List<LocationDto> locationDtos = product.getInventory().stream()
            .map(inventory -> locationMapper.mapObject(inventory.getLocation()))
            .toList();

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