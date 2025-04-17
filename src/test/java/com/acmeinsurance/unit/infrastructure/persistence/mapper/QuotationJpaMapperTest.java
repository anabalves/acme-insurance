package com.acmeinsurance.unit.infrastructure.persistence.mapper;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import com.acmeinsurance.infrastructure.persistence.mapper.QuotationJpaMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class QuotationJpaMapperTest {

    private final QuotationJpaMapper mapper = Mappers.getMapper(QuotationJpaMapper.class);

    @Test
    void shouldMapEntityToDomain() {
        // given
        QuotationEntity entity = QuotationTestFactory.validQuotationEntity();

        // when
        Quotation result = mapper.toDomain(entity);

        // then
        assertThat(result.id()).isEqualTo(entity.getId());
        assertThat(result.productId()).isEqualTo(entity.getProductId());
        assertThat(result.offerId()).isEqualTo(entity.getOfferId());
        assertThat(result.totalMonthlyPremiumAmount()).isEqualTo(entity.getTotalMonthlyPremiumAmount());
        assertThat(result.totalCoverageAmount()).isEqualTo(entity.getTotalCoverageAmount());
        assertThat(result.coverages()).isEqualTo(entity.getCoverages());
        assertThat(result.assistances()).isEqualTo(entity.getAssistances());
        assertThat(result.createdAt()).isEqualTo(entity.getCreatedAt());
        assertThat(result.updatedAt()).isEqualTo(entity.getUpdatedAt());
    }

    @Test
    void shouldMapDomainToEntity() {
        // given
        Quotation domain = QuotationTestFactory.validQuotation();

        // when
        QuotationEntity entity = mapper.toEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(domain.id());
        assertThat(entity.getProductId()).isEqualTo(domain.productId());
        assertThat(entity.getOfferId()).isEqualTo(domain.offerId());
        assertThat(entity.getTotalMonthlyPremiumAmount()).isEqualTo(domain.totalMonthlyPremiumAmount());
        assertThat(entity.getTotalCoverageAmount()).isEqualTo(domain.totalCoverageAmount());
        assertThat(entity.getCoverages()).isEqualTo(domain.coverages());
        assertThat(entity.getAssistances()).isEqualTo(domain.assistances());
        assertThat(entity.getCreatedAt()).isEqualTo(domain.createdAt());
        assertThat(entity.getUpdatedAt()).isEqualTo(domain.updatedAt());
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        // when
        Quotation result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullWhenDomainIsNull() {
        // when
        QuotationEntity result = mapper.toEntity(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapEntityToDomainWithNullCustomer() {
        // given
        QuotationEntity entity = QuotationTestFactory.validQuotationEntity();
        entity.setCustomer(null);

        // when
        Quotation result = mapper.toDomain(entity);

        // then
        assertThat(result.customer()).isNull();
        assertThat(result.id()).isEqualTo(entity.getId());
        assertThat(result.productId()).isEqualTo(entity.getProductId());
        assertThat(result.offerId()).isEqualTo(entity.getOfferId());
        assertThat(result.totalMonthlyPremiumAmount()).isEqualTo(entity.getTotalMonthlyPremiumAmount());
        assertThat(result.totalCoverageAmount()).isEqualTo(entity.getTotalCoverageAmount());
        assertThat(result.coverages()).isEqualTo(entity.getCoverages());
        assertThat(result.assistances()).isEqualTo(entity.getAssistances());
        assertThat(result.createdAt()).isEqualTo(entity.getCreatedAt());
        assertThat(result.updatedAt()).isEqualTo(entity.getUpdatedAt());
    }

    @Test
    void shouldMapDomainToEntityWithNullCustomer() {
        // given
        Quotation quotationWithoutCustomer = QuotationTestFactory.validQuotationWithCustomerNull();

        // when
        QuotationEntity entity = mapper.toEntity(quotationWithoutCustomer);

        // then
        assertThat(entity.getCustomer()).isNull();
        assertThat(entity.getId()).isEqualTo(quotationWithoutCustomer.id());
        assertThat(entity.getProductId()).isEqualTo(quotationWithoutCustomer.productId());
        assertThat(entity.getOfferId()).isEqualTo(quotationWithoutCustomer.offerId());
        assertThat(entity.getTotalMonthlyPremiumAmount())
                .isEqualTo(quotationWithoutCustomer.totalMonthlyPremiumAmount());
        assertThat(entity.getTotalCoverageAmount()).isEqualTo(quotationWithoutCustomer.totalCoverageAmount());
        assertThat(entity.getCoverages()).isEqualTo(quotationWithoutCustomer.coverages());
        assertThat(entity.getAssistances()).isEqualTo(quotationWithoutCustomer.assistances());
        assertThat(entity.getCreatedAt()).isEqualTo(quotationWithoutCustomer.createdAt());
        assertThat(entity.getUpdatedAt()).isEqualTo(quotationWithoutCustomer.updatedAt());
    }
}
