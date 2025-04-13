package com.acmeinsurance.application.usecase;

import com.acmeinsurance.domain.entity.Quotation;

public interface FindQuotationByIdUseCase {

    Quotation execute(Long id);

}
