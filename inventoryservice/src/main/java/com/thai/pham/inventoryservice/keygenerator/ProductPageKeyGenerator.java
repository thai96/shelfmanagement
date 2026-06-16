package com.thai.pham.inventoryservice.keygenerator;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Collectors;

@Component("productPageKeyGenerator")
public class ProductPageKeyGenerator implements KeyGenerator {
    private static final String CUSTOM_KEY_GENERATOR = ":";
    private static final String SORT_KEY_DIVIDER = ";";
    private static final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private static final String PRODUCT_SERVICE_CACHE_PREFIX = "products";
    private static final String PAGE_INDEX_SERVICE_CACHE_PREFIX = "index";
    private static final String PAGE_SIZE_SERVICE_CACHE_PREFIX = "size";
    private static final String PAGE_SORT_SERVICE_CACHE_PREFIX = "sort";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder(INVENTORY_SERVICE_CACHE_PREFIX);
        
        keyBuilder.append(CUSTOM_KEY_GENERATOR).append(PRODUCT_SERVICE_CACHE_PREFIX);
        for(Object param : params) {
            if(param == null) {
                continue;
            }
            
            if(param instanceof Pageable) {
                addPageableParams(keyBuilder, (Pageable) param);
                continue;
            }
            keyBuilder.append(CUSTOM_KEY_GENERATOR).append(param.toString());
        }
        
        return keyBuilder.toString();
    }

    private StringBuilder addPageableParams(StringBuilder builder, Pageable pageable) {
        builder.append(CUSTOM_KEY_GENERATOR).append(PAGE_INDEX_SERVICE_CACHE_PREFIX)
        .append(CUSTOM_KEY_GENERATOR).append(pageable.getPageNumber());
        builder.append(CUSTOM_KEY_GENERATOR).append(PAGE_SIZE_SERVICE_CACHE_PREFIX)
        .append(CUSTOM_KEY_GENERATOR).append(pageable.getPageSize());
        if(pageable.getSort() != null) {
            String sortFormat = pageable.getSort().stream()
                .map(order -> order.getProperty() + "-" + order.getDirection())
                .collect(Collectors.joining(SORT_KEY_DIVIDER));
            builder.append(CUSTOM_KEY_GENERATOR).append(PAGE_SORT_SERVICE_CACHE_PREFIX)
            .append(CUSTOM_KEY_GENERATOR).append(sortFormat);
        }
        return builder;
    }
}