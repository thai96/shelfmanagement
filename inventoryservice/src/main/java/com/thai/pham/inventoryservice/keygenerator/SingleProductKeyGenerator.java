package com.thai.pham.inventoryservice.keygenerator;

@Component("productKeyGenerator")
public class ProductPageKeyGenerator implements KeyGenerator {
    private final String CUSTOM_KEY_GENERATOR = ":";
    private final String SORT_KEY_DIVIDER = ";";
    private final String INVENTORY_SERVICE_CACHE_PREFIX = "inventoryservice";
    private final String PRODUCT_SERVICE_CACHE_PREFIX = "product";
    private final String PRODUCT_SERVICE_ID_CACHE_PREFIX = "id";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder(INVENTORY_SERVICE_CACHE_PREFIX);
        
        keyBuilder.append(CUSTOM_KEY_GENERATOR).append(PRODUCT_SERVICE_CACHE_PREFIX);
        for(Object param : params) {
            if(param == null) {
                continue;
            }
            
            if(param instanceof UUID) {
                keyBuilder.append(CUSTOM_KEY_GENERATOR).append(PRODUCT_SERVICE_ID_CACHE_PREFIX)
                .append(CUSTOM_KEY_GENERATOR).append(param.toString());
                continue;
            }
            keyBuilder.append(CUSTOM_KEY_GENERATOR).append(param.toString());
        }
        
        return keyBuilder.toString();
    }
}