package com.acmeinsurance.infrastructure.messaging.mapper;

import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PolicyIssuedMapper {

    PolicyIssued toDomain(PolicyIssuedEvent event);

}
