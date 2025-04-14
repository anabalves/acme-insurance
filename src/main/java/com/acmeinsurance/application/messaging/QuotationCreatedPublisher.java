package com.acmeinsurance.application.messaging;

import com.acmeinsurance.domain.entity.Quotation;

public interface QuotationCreatedPublisher {

    void publish(Quotation quotation);

}
