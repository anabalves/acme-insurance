package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.CreateQuotationUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import org.springframework.stereotype.Service;

@Service
public class CreateQuotationService implements CreateQuotationUseCase {

    @Override
    public Quotation execute(Quotation quotation) {
        // TO DO
        return quotation;
    }

}
