package com.acmeinsurance.unit.application.service;

import com.acmeinsurance.application.service.FindOfferByIdService;
import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.gateway.OfferCatalogDataGateway;
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
class FindOfferByIdServiceTest {

    @Mock
    private OfferCatalogDataGateway gateway;

    @InjectMocks
    private FindOfferByIdService service;

    @Test
    void shouldReturnOfferWhenActiveAndFound() {
        // given
        Offer offer = QuotationTestFactory.validOffer();
        when(gateway.getOffer(offer.id())).thenReturn(offer);

        // when
        Offer result = service.execute(offer.id());

        // then
        assertThat(result).isEqualTo(offer);
    }

    @Test
    void shouldThrowBusinessExceptionWhenOfferIsInactive() {
        // given
        Offer inactiveOffer = new Offer(QuotationTestFactory.validOfferId(), QuotationTestFactory.validProductId(),
                "Seguro Inativo", QuotationTestFactory.now(), false, QuotationTestFactory.validCatalogCoverages(),
                QuotationTestFactory.validAssistances().stream().toList(),
                QuotationTestFactory.validOffer().monthlyPremiumAmount());

        when(gateway.getOffer(inactiveOffer.id())).thenReturn(inactiveOffer);

        // when & then
        assertThatThrownBy(() -> service.execute(inactiveOffer.id())).isInstanceOf(BusinessException.class)
                .hasMessageContaining("Offer is inactive");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOfferNotFound() {
        // given
        UUID offerId = QuotationTestFactory.validOfferId();
        BusinessException notFound = new BusinessException("Not found", 404);

        when(gateway.getOffer(offerId)).thenThrow(notFound);

        // when & then
        assertThatThrownBy(() -> service.execute(offerId)).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Offer with ID").hasMessageContaining("not found");
    }

    @Test
    void shouldRethrowBusinessExceptionWhenNot404() {
        // given
        UUID offerId = QuotationTestFactory.validOfferId();
        BusinessException internalError = new BusinessException("Timeout", 500);

        when(gateway.getOffer(offerId)).thenThrow(internalError);

        // when & then
        assertThatThrownBy(() -> service.execute(offerId)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("Timeout");
    }
}
