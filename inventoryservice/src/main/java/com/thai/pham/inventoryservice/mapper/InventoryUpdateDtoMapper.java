package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.InventoryUpdateDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.entity.Location;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryUpdateDtoMapper {
    private final LocationDtoMapper locationMapper;

    @Autowired
    public InventoryUpdateDtoMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public Inventory mapEntity(InventoryUpdateDto inventoryDto) {
        val inventoryBuilder = Inventory.builder();
        Optional.ofNullable(inventoryDto.getLocation()).ifPresent(locationDto -> {
            Location location = locationMapper.mapEntity(locationDto);
            inventoryBuilder.location(location);
        });
        inventoryBuilder.id(inventoryDto.getId()).qtyOnHand(inventoryDto.getQtyOnHand()).qtyReserved(inventoryDto.getQtyReserved()).qtyAvailable(inventoryDto.getQtyAvailable());
        return inventoryBuilder.build();
    }

    public InventoryUpdateDto mapObject(Inventory inventory) {
        InventoryUpdateDto inventoryUpdateDto = new InventoryUpdateDto();
        inventoryUpdateDto.setId(inventory.getId());
        inventoryUpdateDto.setLocation(locationMapper.mapObject(inventory.getLocation()));
        inventoryUpdateDto.setQtyAvailable(inventory.getQtyAvailable());
        inventoryUpdateDto.setQtyReserved(inventory.getQtyReserved());
        inventoryUpdateDto.setQtyOnHand(inventory.getQtyOnHand());
        return inventoryUpdateDto;
    }
}