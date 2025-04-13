package com.acmeinsurance.infrastructure.messaging.mapper;

import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PolicyIssuedMapper {

    PolicyIssued toDomain(PolicyIssuedEvent event);

}
