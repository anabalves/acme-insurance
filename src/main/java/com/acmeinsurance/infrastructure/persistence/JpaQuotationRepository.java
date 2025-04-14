package com.acmeinsurance.infrastructure.persistence;

import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuotationRepository extends JpaRepository<QuotationEntity, Long> {
}
