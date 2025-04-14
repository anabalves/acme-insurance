package com.acmeinsurance.infrastructure.messaging.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.messaging.dto.event.QuotationCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuotationCreatedEventMapper {

    @Mapping(source = "id", target = "quotationId")
    @Mapping(source = "customer.documentNumber", target = "customerDocument")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "customer.email", target = "customerEmail")
    QuotationCreatedEvent toEvent(Quotation quotation);

}
