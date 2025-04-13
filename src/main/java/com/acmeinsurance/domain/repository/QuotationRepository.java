package com.acmeinsurance.domain.repository;

import com.acmeinsurance.domain.entity.Quotation;

import java.util.Optional;

public interface QuotationRepository {

    Quotation save(Quotation quotation);
    Optional<Quotation> findById(Long id);
    void updatePolicyId(Long quotationId, Long insurancePolicyId);

}
