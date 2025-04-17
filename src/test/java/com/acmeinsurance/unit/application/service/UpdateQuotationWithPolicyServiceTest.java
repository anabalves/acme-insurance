package com.acmeinsurance.unit.application.service;

import com.acmeinsurance.application.service.UpdateQuotationWithPolicyService;
import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.repository.QuotationRepository;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateQuotationWithPolicyServiceTest {

    @Mock
    private QuotationRepository repository;

    @InjectMocks
    private UpdateQuotationWithPolicyService service;

    @Test
    void shouldUpdateQuotationWithPolicyIdWhenFound() {
        // given
        PolicyIssued event = QuotationTestFactory.validPolicyIssued();
        Quotation existingQuotation = QuotationTestFactory.validQuotation();

        when(repository.findById(event.quotationId())).thenReturn(Optional.of(existingQuotation));

        // when
        service.execute(event);

        // then
        ArgumentCaptor<Quotation> captor = ArgumentCaptor.forClass(Quotation.class);
        verify(repository).save(captor.capture());

        Quotation updated = captor.getValue();
        assertThat(updated.insurancePolicyId()).isEqualTo(event.policyId());
        assertThat(updated.updatedAt()).isNotNull();
    }

    @Test
    void shouldDoNothingWhenQuotationNotFound() {
        // given
        PolicyIssued event = QuotationTestFactory.validPolicyIssued();
        when(repository.findById(event.quotationId())).thenReturn(Optional.empty());

        // when
        service.execute(event);

        // then
        verify(repository, never()).save(any());
    }
}
