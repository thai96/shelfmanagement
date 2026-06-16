package com.thai.pham.inventoryservice.mapper;

@Component
public class InventoryUpdateDtoMapper {
    private LocationDtoMapper locationMapper;

    @Autowired
    public InventoryUpdateDtoMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }
    
    public Inventory mapEntity(InventoryUpdateDto inventoryDto) {
        Inventory entityObject = new Inventory();
        Optional.ofNullable(inventoryDto.getLocation()).ifPresent(locationDto -> {
            Location location = locationMapper.mapEntity(locationDto);
            entityObject.setLocation(location);
        });
        entityObject.setId(inventoryDto.getId());
        entityObject.setQtyOnHand(inventoryDto.getQtyOnHand());
        entityObject.setQtyReserved(inventoryDto.getQtyReserved());
        entityObject.setQtyAvailable(inventoryDto.getAvailable());
        return entityObject;
    }
}