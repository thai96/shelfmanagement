package com.thai.pham.inventoryservice.service;

class RedisService {
    private RedisTemplate<String, UUID> uuidRedisTemplate;
    private RedisTemplate<String, Product> productRedisTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RedisService(@Qualifier("uuidRedisTemplate") RedisTemplate<String, UUID> uuidRedisTemplate) {
        this.uuidRedisTemplate = uuidRedisTemplate;
    }

    public void saveItemIds(String key, List<UUID> itemIds) {
        ListOperation<String, UUID> listOps = uuidRedisTemplate.opsForList();
        Long listSize = listOps.size(key);
        listOps.rightPop(listSize);
        listOps.rightPushAll(key, itemIds);   
    }

    public List<UUID> getItemsIds(String key) {
        ListOperation<String, UUID> listOps = uuidRedisTemplate.opsForList();
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