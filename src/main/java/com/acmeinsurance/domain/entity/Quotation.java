package com.acmeinsurance.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record Quotation(Long id, UUID productId, UUID offerId, Category category, BigDecimal totalMonthlyPremiumAmount,
        BigDecimal totalCoverageAmount, Map<String, BigDecimal> coverages, Set<String> assistances, Customer customer,
        LocalDateTime createdAt, LocalDateTime updatedAt, Long insurancePolicyId) {
}
