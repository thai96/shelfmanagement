package com.thai.pham.inventoryservice.utils;

@Component
public class ProductDtoMapper {
    private final SkuMapper skuMapper;

    public List<ProductInfoDto> toProductInfoDto(List<Product> products) {
        List<ProductInfoDto> result = new LinkedList<>();
        Map<String, List<Product>> productMap = new HashMap<>();
        product.stream().collect((product) -> {
            productMap.computeIfAbsent(product.getProductName(), k -> new ArrayList()).add(product);    
        });
        productMap.entrySet().parallelStream().map((productName, productList)-> {
            List<SkuDto> skus = productList.stream().map(skuMapper::toSkuDto).toList();
            return new ProductInfoDto(
                productName,
                skus
            );
        });
    }
}