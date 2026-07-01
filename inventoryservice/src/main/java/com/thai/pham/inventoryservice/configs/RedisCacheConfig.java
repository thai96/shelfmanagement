package com.thai.pham.inventoryservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisCacheConfig {
    private String host;
    private Integer port;

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
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<Object>(Object.class)));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration).build();
    }
}
