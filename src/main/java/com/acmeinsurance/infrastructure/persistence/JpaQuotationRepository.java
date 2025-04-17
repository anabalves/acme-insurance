package com.acmeinsurance.infrastructure.persistence;

import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaQuotationRepository extends JpaRepository<QuotationEntity, Long> {

    @Query("SELECT q FROM QuotationEntity q " + "LEFT JOIN FETCH q.coverages " + "LEFT JOIN FETCH q.assistances "
            + "WHERE q.id = :id")
    Optional<QuotationEntity> findByIdWithCollections(@Param("id") Long id);
}
