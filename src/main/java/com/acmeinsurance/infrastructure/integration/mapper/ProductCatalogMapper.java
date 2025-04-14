package com.acmeinsurance.infrastructure.integration.mapper;

import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCatalogMapper {

    Product toDomain(ProductCatalogResponse response);

}
