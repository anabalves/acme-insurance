package com.acmeinsurance.infrastructure.cache.redis.config;

import com.acmeinsurance.application.cache.CacheService;
import com.acmeinsurance.infrastructure.cache.redis.RedisCacheService;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class CacheBeansConfig {

    @Bean
    public CacheService<ProductCatalogResponse> productCacheService(
            @Qualifier("productCatalogRedisTemplate") RedisTemplate<String, ProductCatalogResponse> redisTemplate) {
        return new RedisCacheService<>(redisTemplate);
    }

    @Bean
    public CacheService<OfferCatalogResponse> offerCacheService(
            @Qualifier("offerCatalogRedisTemplate") RedisTemplate<String, OfferCatalogResponse> redisTemplate) {
        return new RedisCacheService<>(redisTemplate);
    }
}
