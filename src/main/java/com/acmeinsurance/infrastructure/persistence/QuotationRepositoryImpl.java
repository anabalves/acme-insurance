package com.acmeinsurance.infrastructure.persistence;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.repository.QuotationRepository;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import com.acmeinsurance.infrastructure.persistence.mapper.QuotationJpaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuotationRepositoryImpl implements QuotationRepository {

    private static final Logger log = LoggerFactory.getLogger(QuotationRepositoryImpl.class);

    private final JpaQuotationRepository jpaRepository;
    private final QuotationJpaMapper mapper;

    public QuotationRepositoryImpl(JpaQuotationRepository jpaRepository, QuotationJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Quotation save(Quotation quotation) {
        log.debug("Saving quotation with productId [{}] and offerId [{}]", quotation.productId(), quotation.offerId());
        QuotationEntity entity = mapper.toEntity(quotation);
        QuotationEntity saved = jpaRepository.save(entity);
        log.debug("Quotation with ID [{}] saved successfully", saved.getId());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Quotation> findById(Long id) {
        log.debug("Searching quotation by ID [{}]", id);
        return jpaRepository.findByIdWithCollections(id).map(result -> {
            log.debug("Quotation with ID [{}] found", id);
            return mapper.toDomain(result);
        });
    }

}
