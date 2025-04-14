package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.FindQuotationByIdUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FindQuotationByIdService implements FindQuotationByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(FindQuotationByIdService.class);

    private final QuotationRepository repository;

    public FindQuotationByIdService(QuotationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quotation execute(Long id) {
        log.info("Searching for quotation with ID [{}]", id);
        return repository.findById(id).orElseThrow(() -> {
            log.warn("Quotation with ID [{}] not found", id);
            return new NotFoundException("Quotation with ID [%s] not found".formatted(id));
        });
    }

}
