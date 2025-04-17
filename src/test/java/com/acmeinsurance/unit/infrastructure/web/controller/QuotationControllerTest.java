package com.acmeinsurance.unit.infrastructure.web.controller;

import com.acmeinsurance.application.usecase.CreateQuotationUseCase;
import com.acmeinsurance.application.usecase.FindQuotationByIdUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.web.controller.QuotationController;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;
import com.acmeinsurance.infrastructure.web.mapper.QuotationMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuotationControllerTest {

    @Mock
    private CreateQuotationUseCase createQuotationUseCase;

    @Mock
    private FindQuotationByIdUseCase findQuotationByIdUseCase;

    @Mock
    private QuotationMapper quotationMapper;

    @InjectMocks
    private QuotationController controller;

    @Test
    void shouldCreateQuotation() {
        // given
        QuotationRequest request = QuotationTestFactory.validQuotationRequest();
        Quotation quotation = QuotationTestFactory.validQuotation();
        QuotationResponse response = QuotationTestFactory.validQuotationResponse();

        when(quotationMapper.toDomain(request)).thenReturn(quotation);
        when(createQuotationUseCase.execute(quotation)).thenReturn(quotation);
        when(quotationMapper.toDto(quotation)).thenReturn(response);

        // when
        var result = controller.create(request);

        // then
        assertThat(result.getStatusCode().value()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void shouldReturnQuotationById() {
        // given
        Long id = 1L;
        Quotation quotation = QuotationTestFactory.validQuotation();
        QuotationResponse response = QuotationTestFactory.validQuotationResponse();

        when(findQuotationByIdUseCase.execute(id)).thenReturn(quotation);
        when(quotationMapper.toDto(quotation)).thenReturn(response);

        // when
        var result = controller.findById(id);

        // then
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
    }
}
