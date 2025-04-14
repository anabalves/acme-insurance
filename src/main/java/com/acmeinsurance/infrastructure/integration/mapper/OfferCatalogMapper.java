package com.acmeinsurance.infrastructure.integration.mapper;

import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OfferCatalogMapper {

    Offer toDomain(OfferCatalogResponse response);

}
