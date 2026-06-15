package com.thai.pham.inventoryservice.utils;

@Component
public class ProductInventoryDetailMapper {
    private LocationDtoMapper locationMapper;

    @Autowired
    public ProductInventoryDetailMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }


    public ProductInventoryDetailDto mapObject(Product product) {
        Integer productAvailable = product.getInventory().stream()
            .map(inventory -> findAvailableQuantity(inventory)).sum();
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