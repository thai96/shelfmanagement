package com.thai.pham.inventoryservice.keygenerator;

import org.springframework.stereotype.Component;

@Component
public class IdempotentKeyGenerator {
    private static final String CUSTOM_KEY_GENERATOR = ":";
    private static final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private static final String INVENTORY_SERVICE_API_PREFIX = "api";
    private static final String INVENTORY_SERVICE_VERSION = "v1";
    private static final String IDEMPOTENT_CACHE_PREFIX = "idempotent";

    public String generateKey(String idempotentValue) {
        if(idempotentValue == null || idempotentValue.isBlank()) {
            return null;
        }
        return INVENTORY_SERVICE_CACHE_PREFIX + CUSTOM_KEY_GENERATOR + 
            INVENTORY_SERVICE_API_PREFIX + CUSTOM_KEY_GENERATOR + INVENTORY_SERVICE_VERSION + 
            CUSTOM_KEY_GENERATOR + IDEMPOTENT_CACHE_PREFIX + CUSTOM_KEY_GENERATOR + idempotentValue;
    }
}