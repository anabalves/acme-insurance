package com.acmeinsurance.unit.infrastructure.integration.mapper;

import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.infrastructure.integration.mapper.ProductCatalogMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCatalogMapperTest {

    private final ProductCatalogMapper mapper = Mappers.getMapper(ProductCatalogMapper.class);

    @Test
    void shouldMapProductCatalogResponseToProduct() {
        // given
        ProductCatalogResponse response = QuotationTestFactory.validProductCatalogResponse();

        // when
        Product result = mapper.toDomain(response);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Seguro de Vida");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isTrue();
        assertThat(result.offers()).containsExactly(QuotationTestFactory.validOfferId());
    }

    @Test
    void shouldReturnNullWhenProductCatalogResponseIsNull() {
        // when
        Product result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapProductCatalogResponseWithNullActive() {
        // given
        ProductCatalogResponse response = QuotationTestFactory.productCatalogResponseWithNullActive();

        // when
        Product result = mapper.toDomain(response);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Product with active null");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isFalse();
        assertThat(result.offers()).containsExactly(QuotationTestFactory.validOfferId());
    }

    @Test
    void shouldMapProductCatalogResponseWithNullOffers() {
        // given
        ProductCatalogResponse response = QuotationTestFactory.productCatalogResponseWithNullOffers();

        // when
        Product result = mapper.toDomain(response);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Product with offers null");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isTrue();
        assertThat(result.offers()).isNull();
    }

    @Test
    void shouldMapProductCatalogResponseWithNullActiveAndOffers() {
        // given
        ProductCatalogResponse response = QuotationTestFactory.productCatalogResponseWithNullActiveAndOffers();

        // when
        Product result = mapper.toDomain(response);

        // then
        assertThat(result.id()).isEqualTo(QuotationTestFactory.validProductId());
        assertThat(result.name()).isEqualTo("Product with active and offers null");
        assertThat(result.createdAt()).isEqualTo(QuotationTestFactory.now());
        assertThat(result.active()).isFalse();
        assertThat(result.offers()).isNull();
    }
}
