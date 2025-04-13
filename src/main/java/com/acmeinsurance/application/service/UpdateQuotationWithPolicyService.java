package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.UpdateQuotationWithPolicyUseCase;
import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.domain.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateQuotationWithPolicyService implements UpdateQuotationWithPolicyUseCase {

    private static final Logger log = LoggerFactory.getLogger(UpdateQuotationWithPolicyService.class);

    private final QuotationRepository quotationRepository;

    public UpdateQuotationWithPolicyService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    @Override
    public void execute(PolicyIssued event) {
        log.info("Applying policy [{}] to quotation [{}] at {}", event.policyId(), event.quotationId(),
                event.issuedAt());

        quotationRepository.findById(event.quotationId()).ifPresentOrElse(quotation -> {
            quotation = quotation.withInsurancePolicyId(event.policyId()).withUpdatedAt(LocalDateTime.now());

            quotationRepository.save(quotation);
            log.info("Quotation [{}] updated with policy ID [{}]", event.quotationId(), event.policyId());
        }, () -> log.warn("Quotation [{}] not found, cannot apply policy", event.quotationId()));
    }

}
