package com.acmeinsurance.unit.infrastructure.messaging.mapper;

import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import com.acmeinsurance.infrastructure.messaging.mapper.PolicyIssuedMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyIssuedMapperTest {

    private final PolicyIssuedMapper mapper = Mappers.getMapper(PolicyIssuedMapper.class);

    @Test
    void shouldMapPolicyIssuedEventToDomain() {
        // given
        PolicyIssuedEvent event = QuotationTestFactory.validPolicyIssuedEvent();

        // when
        PolicyIssued result = mapper.toDomain(event);

        // then
        assertThat(result.quotationId()).isEqualTo(event.quotationId());
        assertThat(result.policyId()).isEqualTo(event.policyId());
        assertThat(result.issuedAt()).isEqualTo(event.issuedAt());
    }

    @Test
    void shouldReturnNullWhenPolicyIssuedEventIsNull() {
        // when
        PolicyIssued result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }
}
