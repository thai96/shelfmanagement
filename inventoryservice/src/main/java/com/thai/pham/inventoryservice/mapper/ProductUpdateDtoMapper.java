package com.thai.pham.inventoryservice.mapper;

@Component
public class ProductUpdateDtoMapper {
    private InventoryUpdateDtoMapper inventoryMapper;

    @Autowired
    public ProductUpdateDtoMapper(InventoryUpdateDtoMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    public Product mapEntity(ProductUpdateDto productUpdateDto) {
        ProductAttributes attributes = new ProductAttributes(
            productUpdateDto.getColor(),
            productUpdateDto.getSize()
        );
        List<Inventory> inventories = productUpdateDto.getInventoriesData().stream()
        .map(inventoryMapper::mapEntity).toList();
        Product mappedProduct = new Product(
            productUpdateDto.getId(),
            productUpdateDto.getSku(),
            productUpdateDto.getProductName(),
            attributes,
            inventories
        );

        inventories.stream().forEach(inv -> {
            inv.setProduct(mappedProduct);
        });
        return mappedProduct;
    }
}