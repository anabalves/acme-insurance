package com.acmeinsurance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "acme.insurance.cache-ttl-seconds")
public class CatalogCacheProperties {

    private Long catalogProduct;
    private Long catalogOffer;

    public Long getCatalogProduct() {
        return catalogProduct;
    }

    public void setCatalogProduct(Long catalogProduct) {
        this.catalogProduct = catalogProduct;
    }

    public Long getCatalogOffer() {
        return catalogOffer;
    }

    public void setCatalogOffer(Long catalogOffer) {
        this.catalogOffer = catalogOffer;
    }

}
