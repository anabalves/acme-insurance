package com.acmeinsurance.infrastructure.cache.redis.config;

import com.acmeinsurance.infrastructure.cache.redis.factory.TypedRedisTemplateFactory;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductCatalogResponse> productCatalogRedisTemplate(
            RedisConnectionFactory connectionFactory) {
        return TypedRedisTemplateFactory.create(ProductCatalogResponse.class, connectionFactory);
    }

    @Bean
    public RedisTemplate<String, OfferCatalogResponse> offerCatalogRedisTemplate(
            RedisConnectionFactory connectionFactory) {
        return TypedRedisTemplateFactory.create(OfferCatalogResponse.class, connectionFactory);
    }
}
