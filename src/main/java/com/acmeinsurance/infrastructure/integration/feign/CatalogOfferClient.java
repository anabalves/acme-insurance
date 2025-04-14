package com.acmeinsurance.infrastructure.integration.feign;

import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "offer-catalog", url = "${feign.catalog.offer.api.baseurl}", configuration = FeignClientConfig.class)
public interface CatalogOfferClient {

    @GetMapping("/{id}")
    OfferCatalogResponse findById(@PathVariable UUID id);

}
