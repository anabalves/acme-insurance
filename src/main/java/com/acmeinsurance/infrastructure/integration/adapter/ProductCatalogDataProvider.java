package com.acmeinsurance.infrastructure.integration.adapter;

import com.acmeinsurance.application.cache.CacheService;
import com.acmeinsurance.config.CatalogCacheProperties;
import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.domain.gateway.ProductCatalogDataGateway;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.CatalogProductClient;
import com.acmeinsurance.infrastructure.integration.mapper.ProductCatalogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductCatalogDataProvider implements ProductCatalogDataGateway {

    private static final Logger log = LoggerFactory.getLogger(ProductCatalogDataProvider.class);

    private final CatalogProductClient client;
    private final CacheService<ProductCatalogResponse> cache;
    private final CatalogCacheProperties cacheProperties;
    private final ProductCatalogMapper mapper;

    public ProductCatalogDataProvider(CatalogProductClient client, CacheService<ProductCatalogResponse> cache,
            CatalogCacheProperties cacheProperties, ProductCatalogMapper mapper) {
        this.client = client;
        this.cache = cache;
        this.cacheProperties = cacheProperties;
        this.mapper = mapper;
    }

    @Override
    public Product getProduct(UUID id) {
        String key = "catalog:product:" + id;
        ProductCatalogResponse response = cache.get(key);

        if (response != null) {
            log.info("Product [{}] loaded from cache", id);
        } else {
            log.info("Product [{}] not found in cache. Calling catalog API...", id);
            response = client.findById(id);
            cache.put(key, response, cacheProperties.getCatalogProduct());
        }

        return mapper.toDomain(response);
    }

}