package com.acmeinsurance.infrastructure.web.mapper;

import com.acmeinsurance.domain.entity.Customer;
import com.acmeinsurance.infrastructure.web.dto.request.CustomerRequest;
import com.acmeinsurance.infrastructure.web.dto.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer toDomain(CustomerRequest request);

    CustomerResponse toDto(Customer customer);

}
