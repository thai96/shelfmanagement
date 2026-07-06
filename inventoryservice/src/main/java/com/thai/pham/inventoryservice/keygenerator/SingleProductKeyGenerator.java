package com.thai.pham.inventoryservice.keygenerator;

import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component("productKeyGenerator")
public class SingleProductKeyGenerator implements KeyGenerator {
    private static final String CUSTOM_KEY_GENERATOR = ":";
    private static final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private static final String INVENTORY_SERVICE_API_PREFIX = "api";
    private static final String INVENTORY_SERVICE_VERSION = "v1";
    private static final String PRODUCT_SERVICE_CACHE_PREFIX = "product";
    private static final String PRODUCT_SERVICE_ID_CACHE_PREFIX = "id";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder(INVENTORY_SERVICE_CACHE_PREFIX);
        
        keyBuilder
            .append(CUSTOM_KEY_GENERATOR).append(INVENTORY_SERVICE_API_PREFIX)
            .append(CUSTOM_KEY_GENERATOR).append(INVENTORY_SERVICE_VERSION)
            .append(CUSTOM_KEY_GENERATOR).append(PRODUCT_SERVICE_CACHE_PREFIX);
        for(Object param : params) {
            if(param == null) {
                continue;
            }
            
            if(param instanceof UUID) {
                keyBuilder.append(CUSTOM_KEY_GENERATOR).append(param);
                break;
            }

            if(param instanceof Product) {
                keyBuilder.append(CUSTOM_KEY_GENERATOR).append(((Product) param).getId().toString());
                break;
            }
        }
        
        return keyBuilder.toString();
    }
}