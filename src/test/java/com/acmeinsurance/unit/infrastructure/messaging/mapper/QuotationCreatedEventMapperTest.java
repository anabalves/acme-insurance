package com.acmeinsurance.unit.infrastructure.messaging.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.messaging.dto.event.QuotationCreatedEvent;
import com.acmeinsurance.infrastructure.messaging.mapper.QuotationCreatedEventMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class QuotationCreatedEventMapperTest {

    private final QuotationCreatedEventMapper mapper = Mappers.getMapper(QuotationCreatedEventMapper.class);

    @Test
    void shouldMapQuotationToQuotationCreatedEvent() {
        // given
        Quotation quotation = QuotationTestFactory.validQuotation();

        // when
        QuotationCreatedEvent result = mapper.toEvent(quotation);

        // then
        assertThat(result.quotationId()).isEqualTo(quotation.id());
        assertThat(result.productId()).isEqualTo(quotation.productId());
        assertThat(result.offerId()).isEqualTo(quotation.offerId());
        assertThat(result.category()).isEqualTo(quotation.category().name());
        assertThat(result.totalMonthlyPremiumAmount()).isEqualTo(quotation.totalMonthlyPremiumAmount());
        assertThat(result.totalCoverageAmount()).isEqualTo(quotation.totalCoverageAmount());
        assertThat(result.coverages()).isEqualTo(quotation.coverages());
        assertThat(result.assistances()).isEqualTo(quotation.assistances());
        assertThat(result.customerDocument()).isEqualTo(quotation.customer().documentNumber());
        assertThat(result.customerName()).isEqualTo(quotation.customer().name());
        assertThat(result.customerEmail()).isEqualTo(quotation.customer().email());
        assertThat(result.createdAt()).isEqualTo(quotation.createdAt());
    }

    @Test
    void shouldReturnNullWhenQuotationIsNull() {
        // when
        QuotationCreatedEvent result = mapper.toEvent(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapQuotationToQuotationCreatedEventWithNullCategoryCoveragesAssistancesAndPartialCustomer() {
        // given
        Quotation quotation = QuotationTestFactory.quotationWithNullFieldsAndPartialCustomer();

        // when
        QuotationCreatedEvent result = mapper.toEvent(quotation);

        // then
        assertThat(result.category()).isNull();
        assertThat(result.coverages()).isNull();
        assertThat(result.assistances()).isNull();
        assertThat(result.customerDocument()).isNull();
        assertThat(result.customerName()).isNull();
        assertThat(result.customerEmail()).isNull();
    }
}
