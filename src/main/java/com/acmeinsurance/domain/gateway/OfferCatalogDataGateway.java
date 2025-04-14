package com.acmeinsurance.domain.gateway;

import com.acmeinsurance.domain.entity.Offer;

import java.util.UUID;

public interface OfferCatalogDataGateway {

    Offer getOffer(UUID id);

}
