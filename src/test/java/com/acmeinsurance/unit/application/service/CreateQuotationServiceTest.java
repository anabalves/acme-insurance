package com.acmeinsurance.unit.application.service;

import com.acmeinsurance.application.messaging.QuotationCreatedPublisher;
import com.acmeinsurance.application.service.CreateQuotationService;
import com.acmeinsurance.application.usecase.FindOfferByIdUseCase;
import com.acmeinsurance.application.usecase.FindProductByIdUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.repository.QuotationRepository;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateQuotationServiceTest {

    @Mock
    private QuotationRepository repository;

    @Mock
    private FindProductByIdUseCase findProductByIdUseCase;

    @Mock
    private FindOfferByIdUseCase findOfferByIdUseCase;

    @Mock
    private QuotationCreatedPublisher publisher;

    @InjectMocks
    private CreateQuotationService service;

    @Test
    void shouldCreateQuotationSuccessfullyWhenValid() {
        // given
        var quotation = QuotationTestFactory.validQuotation();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);
        when(repository.save(any(Quotation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        var result = service.execute(quotation);

        // then
        verify(repository).save(any(Quotation.class));
        verify(publisher).publish(any(Quotation.class));
    }

    @Test
    void shouldThrowExceptionWhenOfferDoesNotBelongToProduct() {
        // given
        var quotation = QuotationTestFactory.validQuotation();
        var product = QuotationTestFactory.validProductWithDifferentOffer();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("Offer does not belong");
    }

    @Test
    void shouldThrowExceptionWhenCoverageExceedsMaxAllowedValue() {
        // given
        var quotation = QuotationTestFactory.quotationWithExceedingCoverage();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("exceeds max allowed value");
    }

    @Test
    void shouldThrowExceptionWhenCoverageIsNotInOffer() {
        // given
        var quotation = QuotationTestFactory.quotationWithInvalidCoverage();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("is not available in the offer");
    }

    @Test
    void shouldThrowExceptionWhenAssistanceIsNotInOffer() {
        // given
        var quotation = QuotationTestFactory.quotationWithInvalidAssistance();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("is not available in the offer");
    }

    @Test
    void shouldThrowExceptionWhenPremiumIsBelowMinimum() {
        // given
        var quotation = QuotationTestFactory.quotationWithPremiumBelowMinimum();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("outside the allowed range");
    }

    @Test
    void shouldThrowExceptionWhenPremiumIsAboveMaximum() {
        // given
        var quotation = QuotationTestFactory.quotationWithPremiumAboveMaximum();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("outside the allowed range");
    }

    @Test
    void shouldThrowExceptionWhenTotalCoverageAmountIsIncorrect() {
        // given
        var quotation = QuotationTestFactory.quotationWithWrongTotalCoverage();
        var product = QuotationTestFactory.validProduct();
        var offer = QuotationTestFactory.validOffer();

        when(findProductByIdUseCase.execute(quotation.productId())).thenReturn(product);
        when(findOfferByIdUseCase.execute(quotation.offerId())).thenReturn(offer);

        // when & then
        assertThatThrownBy(() -> service.execute(quotation)).isInstanceOf(BusinessException.class)
                .hasMessageContaining("does not match the sum");
    }
}
