package com.acmeinsurance.infrastructure.persistence;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.repository.QuotationRepository;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import com.acmeinsurance.infrastructure.persistence.mapper.QuotationJpaMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuotationRepositoryImpl implements QuotationRepository {

    private final JpaQuotationRepository jpaRepository;
    private final QuotationJpaMapper mapper;

    public QuotationRepositoryImpl(JpaQuotationRepository jpaRepository, QuotationJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Quotation save(Quotation quotation) {
        QuotationEntity entity = mapper.toEntity(quotation);
        QuotationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Quotation> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

}
