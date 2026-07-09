package com.thai.pham.inventoryservice.keygenerator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.thai.pham.inventoryservice.entity.Inventory;

@Service
public class InventoryKeyGenerator {
    private static final String CUSTOM_KEY_DIVIDER = ":";
    private static final String SORT_KEY_DIVIDER = ";";
    private static final String INVENTORY_PACKAGE_CACHE_PREFIX = "inventoryservice";
    private static final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private static final String INVENTORY_SINGLE_CACHE_PERFIX = "inventory";
    private static final String PAGE_INDEX_SERVICE_CACHE_PREFIX = "index";
    private static final String PAGE_SIZE_SERVICE_CACHE_PREFIX = "size";
    private static final String PAGE_SEARCH_SERVICE_CACHE_PREFIX = "search";
    private static final String PAGE_SORT_SERVICE_CACHE_PREFIX = "sort";

    public String generatePageKey(Pageable page) {
        StringBuilder keyBuilder = new StringBuilder(INVENTORY_PACKAGE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + INVENTORY_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER);
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

    public String generateSingleInventoryKey(Inventory inventory) {
        if(inventory == null) {
            return null;
        }
        return INVENTORY_PACKAGE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + 
                INVENTORY_SERVICE_CACHE_PREFIX + CUSTOM_KEY_DIVIDER + 
                INVENTORY_SINGLE_CACHE_PERFIX + CUSTOM_KEY_DIVIDER +
                inventory.getId();
    }
}