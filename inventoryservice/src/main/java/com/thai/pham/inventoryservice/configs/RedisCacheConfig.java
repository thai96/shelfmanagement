package com.thai.pham.inventoryservice.configs;

import com.thai.pham.inventoryservice.dto.InventoryDto;
import com.thai.pham.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@EnableCaching
public class RedisCacheConfig {
    private final String host;
    private final Integer port;

    @Autowired
    public RedisCacheConfig(
        @Value("${spring.data.redis.host}") String host,
        @Value("${spring.data.redis.port}") Integer port
    ) {
        this.host = host;
        this.port = port;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host, port);
        factory.setShareNativeConnection(true);
        return factory;
    }

    @Bean
    public RedisCacheManager cacheConfiguration(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(Object.class)));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration).build();
    }

    @Bean("uuidRedisTemplate")
    public RedisTemplate<String, UUID> uuidRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UUID> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean("productRedisTemplate")
    public RedisTemplate<String, Product> productRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Product> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        GenericJacksonJsonRedisSerializer jackson2JsonRedisSerializer = new GenericJacksonJsonRedisSerializer(new ObjectMapper());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean("inventoryRedisTemplate")
    public RedisTemplate<String, InventoryDto> inventoryDtoRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, InventoryDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
