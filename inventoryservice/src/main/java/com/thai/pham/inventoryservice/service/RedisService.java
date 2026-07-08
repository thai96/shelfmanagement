package com.thai.pham.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RedisService {
    private final RedisTemplate<String, UUID> uuidRedisTemplate;
    private final RedisTemplate<String, Product> productRedisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisService(
            @Qualifier("uuidRedisTemplate") RedisTemplate<String, UUID> uuidRedisTemplate,
            @Qualifier("productRedisTemplate") RedisTemplate<String, Product> productRedisTemplate
    ) {
        this.uuidRedisTemplate = uuidRedisTemplate;
        this.productRedisTemplate = productRedisTemplate;
    }

    public void saveItemIds(String key, List<UUID> itemIds) {
        ListOperations<String, UUID> listOps = uuidRedisTemplate.opsForList();
        Long listSize = listOps.size(key);
        listOps.rightPop(key, listSize);
        listOps.rightPushAll(key, itemIds);   
    }

    public List<UUID> getItemsIds(String key) {
        ListOperations<String, UUID> listOps = uuidRedisTemplate.opsForList();
        Long listSize = listOps.size(key);
        return listOps.range(key, 0, listSize - 1);
    }

    public Boolean deleteItemsIds(String key) {
        return uuidRedisTemplate.delete(key);
    }

    public void saveProducts(Map<String, Product> cacheValue) {
        productRedisTemplate.opsForValue().multiSet(cacheValue);
    }
}