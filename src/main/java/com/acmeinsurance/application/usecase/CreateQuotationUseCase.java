package com.acmeinsurance.application.usecase;

import com.acmeinsurance.domain.entity.Quotation;

public interface CreateQuotationUseCase {

    Quotation execute(Quotation quotation);

}
