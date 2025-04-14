package com.acmeinsurance.infrastructure.persistence.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuotationJpaMapper {

    Quotation toDomain(QuotationEntity entity);
    QuotationEntity toEntity(Quotation domain);

}
