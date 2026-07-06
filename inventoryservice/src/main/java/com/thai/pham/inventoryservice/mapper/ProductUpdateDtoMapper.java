package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.InventoryUpdateDto;
import com.thai.pham.inventoryservice.dto.ProductUpdateDto;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.entity.Product;
import com.thai.pham.inventoryservice.entity.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductUpdateDtoMapper {
    private final InventoryUpdateDtoMapper inventoryMapper;

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

        inventories.forEach(inv -> inv.setProduct(mappedProduct));
        return mappedProduct;
    }

    public ProductUpdateDto mapObject(Product product) {
        ProductUpdateDto updateDto = new ProductUpdateDto();
        List<InventoryUpdateDto> inventories = product.getInventory().stream()
                .map(inventoryMapper::mapObject).toList();
        updateDto.setInventoriesData(inventories);
        updateDto.setId(product.getId());
        updateDto.setSku(product.getSku());
        updateDto.setColor(product.getProductAttributes().getColor());
        updateDto.setSize(product.getProductAttributes().getSize());
        updateDto.setProductName(product.getProductName());
        return updateDto;
    }
}