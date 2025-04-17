package com.acmeinsurance.unit.infrastructure.web.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;
import com.acmeinsurance.infrastructure.web.mapper.CustomerMapperImpl;
import com.acmeinsurance.infrastructure.web.mapper.QuotationMapper;
import com.acmeinsurance.infrastructure.web.mapper.QuotationMapperImpl;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuotationMapperImpl.class, CustomerMapperImpl.class})
class QuotationMapperTest {

    @Autowired
    private QuotationMapper mapper;

    @Test
    void shouldMapQuotationRequestToDomain() {
        // given
        QuotationRequest request = QuotationTestFactory.validQuotationRequest();

        // when
        Quotation result = mapper.toDomain(request);

        // then
        assertThat(result.productId()).isEqualTo(QuotationTestFactory.PRODUCT_ID);
        assertThat(result.offerId()).isEqualTo(QuotationTestFactory.OFFER_ID);
        assertThat(result.category().name()).isEqualTo(request.category());
        assertThat(result.totalMonthlyPremiumAmount()).isEqualTo(request.totalMonthlyPremiumAmount());
        assertThat(result.totalCoverageAmount()).isEqualTo(request.totalCoverageAmount());
        assertThat(result.coverages()).isEqualTo(request.coverages());
        assertThat(result.assistances()).containsExactlyInAnyOrderElementsOf(request.assistances());
        assertThat(result.customer().documentNumber()).isEqualTo(request.customer().documentNumber());
    }

    @Test
    void shouldMapQuotationRequestToDomainWithNullCategoryCoveragesAndAssistances() {
        // given
        QuotationRequest request = QuotationTestFactory.quotationRequestWithNullFields();

        // when
        Quotation result = mapper.toDomain(request);

        // then
        assertThat(result.category()).isNull();
        assertThat(result.coverages()).isNull();
        assertThat(result.assistances()).isNull();
    }

    @Test
    void shouldMapQuotationToQuotationResponse() {
        // given
        Quotation quotation = QuotationTestFactory.validQuotation();

        // when
        QuotationResponse response = mapper.toDto(quotation);

        // then
        assertThat(response.id()).isEqualTo(quotation.id());
        assertThat(response.insurancePolicyId()).isEqualTo(quotation.insurancePolicyId());
        assertThat(response.productId()).isEqualTo(quotation.productId());
        assertThat(response.offerId()).isEqualTo(quotation.offerId());
        assertThat(response.category()).isEqualTo(quotation.category().name());
        assertThat(response.totalMonthlyPremiumAmount()).isEqualTo(quotation.totalMonthlyPremiumAmount());
        assertThat(response.totalCoverageAmount()).isEqualTo(quotation.totalCoverageAmount());
        assertThat(response.coverages()).isEqualTo(quotation.coverages());
        assertThat(response.assistances()).containsExactlyInAnyOrderElementsOf(quotation.assistances());
        assertThat(response.customer().documentNumber()).isEqualTo(quotation.customer().documentNumber());
    }

    @Test
    void shouldMapQuotationToQuotationResponseWithNullCategoryCoveragesAndAssistances() {
        // given
        Quotation quotation = QuotationTestFactory.quotationWithNullFields();

        // when
        QuotationResponse response = mapper.toDto(quotation);

        // then
        assertThat(response.category()).isNull();
        assertThat(response.coverages()).isNull();
        assertThat(response.assistances()).isNull();
    }

    @Test
    void shouldReturnNullWhenQuotationRequestIsNull() {
        // when
        Quotation result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullWhenQuotationIsNull() {
        // when
        QuotationResponse result = mapper.toDto(null);

        // then
        assertThat(result).isNull();
    }
}
