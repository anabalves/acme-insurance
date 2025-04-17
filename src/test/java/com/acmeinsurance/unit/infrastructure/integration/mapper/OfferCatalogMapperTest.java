package com.acmeinsurance.unit.infrastructure.integration.mapper;

import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.mapper.OfferCatalogMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OfferCatalogMapperTest {

    private final OfferCatalogMapper mapper = Mappers.getMapper(OfferCatalogMapper.class);

    @Test
    void shouldMapOfferCatalogResponseToOffer() {
        // given
        OfferCatalogResponse response = QuotationTestFactory.validOfferCatalogResponse();

        // when
        Offer result = mapper.toDomain(response);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validOfferId());
        assertThat(result.productId()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Seguro de Vida Familiar");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isTrue();
        assertThat(result.coverages()).containsKey("Incêndio");
        assertThat(result.assistances()).contains("Encanador");
        assertThat(result.monthlyPremiumAmount().minAmount()).isEqualTo(new BigDecimal("50.00"));
    }

    @Test
    void shouldReturnNullWhenOfferCatalogResponseIsNull() {
        // when
        Offer result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapOfferCatalogResponseWithNullFields() {
        // given
        OfferCatalogResponse responseWithNulls = QuotationTestFactory.validOfferCatalogResponseWithNullFields();

        // when
        Offer result = mapper.toDomain(responseWithNulls);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validOfferId());
        assertThat(result.productId()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Offer with null fields");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isTrue();
        assertThat(result.coverages()).isNull();
        assertThat(result.assistances()).isNull();
        assertThat(result.monthlyPremiumAmount()).isNull();
    }
}
