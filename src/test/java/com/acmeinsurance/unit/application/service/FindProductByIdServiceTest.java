package com.acmeinsurance.unit.application.service;

import com.acmeinsurance.application.service.FindProductByIdService;
import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.gateway.ProductCatalogDataGateway;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductByIdServiceTest {

    @Mock
    private ProductCatalogDataGateway gateway;

    @InjectMocks
    private FindProductByIdService service;

    @Test
    void shouldReturnProductWhenActiveAndFound() {
        // given
        Product product = QuotationTestFactory.validProduct();
        when(gateway.getProduct(product.id())).thenReturn(product);

        // when
        Product result = service.execute(product.id());

        // then
        assertThat(result).isEqualTo(product);
    }

    @Test
    void shouldThrowBusinessExceptionWhenProductIsInactive() {
        // given
        Product inactiveProduct = QuotationTestFactory.inactiveProduct();
        when(gateway.getProduct(inactiveProduct.id())).thenReturn(inactiveProduct);

        // when & then
        assertThatThrownBy(() -> service.execute(inactiveProduct.id())).isInstanceOf(BusinessException.class)
                .hasMessageContaining("Product is inactive");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenProductNotFound() {
        // given
        UUID productId = QuotationTestFactory.validProductId();
        BusinessException notFound = new BusinessException("Not found", 404);

        when(gateway.getProduct(productId)).thenThrow(notFound);

        // when & then
        assertThatThrownBy(() -> service.execute(productId)).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product with ID").hasMessageContaining("not found");
    }

    @Test
    void shouldRethrowBusinessExceptionWhenNot404() {
        // given
        UUID productId = QuotationTestFactory.validProductId();
        BusinessException timeout = new BusinessException("Timeout", 503);

        when(gateway.getProduct(productId)).thenThrow(timeout);

        // when & then
        assertThatThrownBy(() -> service.execute(productId)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("Timeout");
    }
}
