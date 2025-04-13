package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.FindQuotationByIdUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.repository.QuotationRepository;
import org.springframework.stereotype.Service;

@Service
public class FindQuotationByIdService implements FindQuotationByIdUseCase {

    private final QuotationRepository repository;

    public FindQuotationByIdService(QuotationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Quotation execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quotation with ID %d not found".formatted(id)));
    }

}
