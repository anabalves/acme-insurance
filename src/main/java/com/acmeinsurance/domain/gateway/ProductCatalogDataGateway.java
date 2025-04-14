package com.acmeinsurance.domain.gateway;

import com.acmeinsurance.domain.entity.Product;

import java.util.UUID;

public interface ProductCatalogDataGateway {

    Product getProduct(UUID id);

}
