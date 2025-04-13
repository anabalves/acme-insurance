package com.acmeinsurance.infrastructure.web.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { CustomerMapper.class })
public interface QuotationMapper {

    Quotation toDomain(QuotationRequest request);

    QuotationResponse toDto(Quotation quotation);

}
