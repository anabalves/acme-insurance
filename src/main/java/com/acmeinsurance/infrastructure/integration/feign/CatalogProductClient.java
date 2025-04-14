package com.acmeinsurance.infrastructure.integration.feign;

import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-catalog", url = "${feign.catalog.product.api.baseurl}", configuration = FeignClientConfig.class)
public interface CatalogProductClient {

    @GetMapping("/{id}")
    ProductCatalogResponse findById(@PathVariable UUID id);

}
