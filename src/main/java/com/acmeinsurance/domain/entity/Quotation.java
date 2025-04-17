package com.acmeinsurance.domain.entity;

import com.acmeinsurance.domain.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record Quotation(Long id, UUID productId, UUID offerId, Category category, BigDecimal totalMonthlyPremiumAmount,
        BigDecimal totalCoverageAmount, Map<String, BigDecimal> coverages, Set<String> assistances, Customer customer,
        LocalDateTime createdAt, LocalDateTime updatedAt, Long insurancePolicyId) {
    public Quotation withInsurancePolicyId(Long insurancePolicyId) {
        return new Quotation(id, productId, offerId, category, totalMonthlyPremiumAmount, totalCoverageAmount,
                coverages, assistances, customer, createdAt, updatedAt, insurancePolicyId);
    }

    public Quotation withCreatedAt(LocalDateTime createdAt) {
        return new Quotation(id, productId, offerId, category, totalMonthlyPremiumAmount, totalCoverageAmount,
                coverages, assistances, customer, createdAt, updatedAt, insurancePolicyId);
    }

    public Quotation withUpdatedAt(LocalDateTime updatedAt) {
        return new Quotation(id, productId, offerId, category, totalMonthlyPremiumAmount, totalCoverageAmount,
                coverages, assistances, customer, createdAt, updatedAt, insurancePolicyId);
    }
}
