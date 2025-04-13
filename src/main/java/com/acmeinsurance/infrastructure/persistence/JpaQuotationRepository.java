package com.acmeinsurance.infrastructure.persistence;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.repository.QuotationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuotationRepository extends QuotationRepository, JpaRepository<Quotation, Long> {
}
