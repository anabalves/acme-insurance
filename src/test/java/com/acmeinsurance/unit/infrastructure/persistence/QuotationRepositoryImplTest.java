package com.acmeinsurance.unit.infrastructure.persistence;

import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.persistence.JpaQuotationRepository;
import com.acmeinsurance.infrastructure.persistence.QuotationRepositoryImpl;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import com.acmeinsurance.infrastructure.persistence.mapper.QuotationJpaMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuotationRepositoryImplTest {

    @Mock
    private JpaQuotationRepository jpaRepository;

    @Mock
    private QuotationJpaMapper mapper;

    @InjectMocks
    private QuotationRepositoryImpl repository;

    @Test
    void shouldSaveAndReturnQuotation() {
        // given
        Quotation quotation = QuotationTestFactory.validQuotation();
        QuotationEntity entity = QuotationTestFactory.validQuotationEntity();

        when(mapper.toEntity(quotation)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(quotation);

        // when
        Quotation result = repository.save(quotation);

        // then
        assertThat(result).isEqualTo(quotation);
    }

    @Test
    void shouldFindByIdAndReturnQuotation() {
        // given
        Long id = 1L;
        QuotationEntity entity = QuotationTestFactory.validQuotationEntity();
        Quotation quotation = QuotationTestFactory.validQuotation();

        when(jpaRepository.findByIdWithCollections(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(quotation);

        // when
        Optional<Quotation> result = repository.findById(id);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(quotation);
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        // given
        Long id = 99L;
        when(jpaRepository.findByIdWithCollections(id)).thenReturn(Optional.empty());

        // when
        Optional<Quotation> result = repository.findById(id);

        // then
        assertThat(result).isEmpty();
    }
}
