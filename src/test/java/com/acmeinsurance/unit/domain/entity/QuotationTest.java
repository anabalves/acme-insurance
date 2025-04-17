package com.acmeinsurance.unit.domain.entity;

import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class QuotationTest {

    @Test
    void shouldReturnNewInstanceWithUpdatedInsurancePolicyId() {
        // given
        var original = QuotationTestFactory.validQuotation();
        Long newPolicyId = 12345L;

        // when
        var updated = original.withInsurancePolicyId(newPolicyId);

        // then
        assertThat(updated).isNotSameAs(original);
        assertThat(updated.insurancePolicyId()).isEqualTo(newPolicyId);
        assertThat(updated.id()).isEqualTo(original.id());
    }

    @Test
    void shouldReturnNewInstanceWithUpdatedCreatedAt() {
        // given
        var original = QuotationTestFactory.validQuotation();
        var newCreatedAt = LocalDateTime.now().minusDays(1);

        // when
        var updated = original.withCreatedAt(newCreatedAt);

        // then
        assertThat(updated.createdAt()).isEqualTo(newCreatedAt);
        assertThat(updated).isNotSameAs(original);
    }

    @Test
    void shouldReturnNewInstanceWithUpdatedUpdatedAt() {
        // given
        var original = QuotationTestFactory.validQuotation();
        var newUpdatedAt = LocalDateTime.now();

        // when
        var updated = original.withUpdatedAt(newUpdatedAt);

        // then
        assertThat(updated.updatedAt()).isEqualTo(newUpdatedAt);
        assertThat(updated).isNotSameAs(original);
    }
}
