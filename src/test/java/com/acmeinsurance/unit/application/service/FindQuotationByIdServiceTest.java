package com.acmeinsurance.unit.application.service;

import com.acmeinsurance.application.service.FindQuotationByIdService;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.repository.QuotationRepository;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindQuotationByIdServiceTest {

    @Mock
    private QuotationRepository repository;

    @InjectMocks
    private FindQuotationByIdService service;

    @Test
    void shouldReturnQuotationWhenExists() {
        // given
        Quotation quotation = QuotationTestFactory.validQuotation();
        when(repository.findById(quotation.id())).thenReturn(Optional.of(quotation));

        // when
        Quotation result = service.execute(quotation.id());

        // then
        assertThat(result).isEqualTo(quotation);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenQuotationDoesNotExist() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> service.execute(id)).isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Quotation with ID").hasMessageContaining("not found");
    }
}
