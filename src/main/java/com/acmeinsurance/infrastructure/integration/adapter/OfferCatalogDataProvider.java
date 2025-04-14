package com.acmeinsurance.infrastructure.integration.adapter;

import com.acmeinsurance.application.cache.CacheService;
import com.acmeinsurance.config.CatalogCacheProperties;
import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.domain.gateway.OfferCatalogDataGateway;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.CatalogOfferClient;
import com.acmeinsurance.infrastructure.integration.mapper.OfferCatalogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OfferCatalogDataProvider implements OfferCatalogDataGateway {

    private static final Logger log = LoggerFactory.getLogger(OfferCatalogDataProvider.class);

    private final CatalogOfferClient client;
    private final CacheService<OfferCatalogResponse> cache;
    private final CatalogCacheProperties cacheProperties;
    private final OfferCatalogMapper mapper;

    public OfferCatalogDataProvider(CatalogOfferClient client, CacheService<OfferCatalogResponse> cache,
            CatalogCacheProperties cacheProperties, OfferCatalogMapper mapper) {
        this.client = client;
        this.cache = cache;
        this.cacheProperties = cacheProperties;
        this.mapper = mapper;
    }

    @Override
    public Offer getOffer(UUID id) {
        String key = "catalog:offer:" + id;
        OfferCatalogResponse response = cache.get(key);

        if (response != null) {
            log.info("Offer [{}] loaded from cache", id);
        } else {
            log.info("Offer [{}] not found in cache. Calling catalog API...", id);
            response = client.findById(id);
            cache.put(key, response, cacheProperties.getCatalogOffer());
        }

        return mapper.toDomain(response);
    }

}
