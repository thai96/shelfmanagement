package com.thai.pham.inventoryservice.keygenerator;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductKeyGenerator {
    private static final String CUSTOM_KEY_DIVIDER = ":";
    private static final String SORT_KEY_DIVIDER = ";";
    private static final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private static final String PRODUCT_SERVICE_CACHE_PREFIX = "productservice";
    private static final String PRODUCT_SINGLE_CACHE_PERFIX = "product";
    private static final String PAGE_INDEX_SERVICE_CACHE_PREFIX = "index";
    private static final String PAGE_SIZE_SERVICE_CACHE_PREFIX = "size";
    private static final String PAGE_SEARCH_SERVICE_CACHE_PREFIX = "search";
    private static final String PAGE_SORT_SERVICE_CACHE_PREFIX = "sort";

    public String generatePageKey(String searchTerm, Pageable pageInfo) {
        StringBuilder keyBuilder = new StringBuilder(INVENTORY_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + PRODUCT_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER);
        if(pageInfo == null || pageInfo.isUnpaged()) {
            return keyBuilder.toString();
        }
        keyBuilder.append(PAGE_INDEX_SERVICE_CACHE_PREFIX).append(CUSTOM_KEY_DIVIDER).append(pageInfo.getPageNumber())
        .append(PAGE_SIZE_SERVICE_CACHE_PREFIX).append(CUSTOM_KEY_DIVIDER).append(pageInfo.getPageSize());
        if(!searchTerm.isBlank()) {
            keyBuilder.append(CUSTOM_KEY_DIVIDER).append(PAGE_SEARCH_SERVICE_CACHE_PREFIX)
            .append(CUSTOM_KEY_DIVIDER).append(searchTerm);
        }
        String sortKey = Optional.of(pageInfo.getSort()).map(sort ->
            sort.stream().sorted(Comparator.comparing(Sort.Order::getProperty))
            .map(order -> order.getProperty() + "-" + order.getDirection())
            .collect(Collectors.joining(SORT_KEY_DIVIDER))
        ).orElse("");
        if(!sortKey.isBlank()) {
            keyBuilder.append(PAGE_SORT_SERVICE_CACHE_PREFIX).append(CUSTOM_KEY_DIVIDER).append(sortKey);
        }
        return keyBuilder.toString();
    }

    public String generateSingleProductKey(Product product) {
        if(product == null) {
            return null;
        }
        return INVENTORY_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + 
                PRODUCT_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + 
                PRODUCT_SINGLE_CACHE_PERFIX + CUSTOM_KEY_DIVIDER +
                product.getId();
    }
}