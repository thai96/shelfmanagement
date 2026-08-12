package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.ProductResult;
import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductResultMapper implements BaseMapper<Product, ProductResult> {
    private ProductAttributeMapper attributeMapper;

    @Autowired
    public ProductResultMapper(ProductAttributeMapper attributeMapper) {
        this.attributeMapper = attributeMapper;
    }

    @Override
    public Product mapEntity(ProductResult productResult) {
        return new Product(
                productResult.getId(),
                productResult.getSku(),
                productResult.getProductName(),
                attributeMapper.mapEntity(productResult.getAttributes())
        );
    }

    @Override
    public ProductResult mapObject(Product product) {
        return new ProductResult(
                product.getId(),
                product.getProductName(),
                product.getSku(),
                attributeMapper.mapObject(product.getProductAttributes()),
                product.getCreatedAt().toString(),
                product.getUpdatedAt().toString()
        );
    }
}
