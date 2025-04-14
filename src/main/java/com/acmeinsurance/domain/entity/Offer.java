package com.acmeinsurance.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record Offer(UUID id, UUID productId, String name, LocalDateTime createdAt, Boolean active,
        Map<String, BigDecimal> coverages, List<String> assistances, MonthlyPremiumAmount monthlyPremiumAmount) {

    public record MonthlyPremiumAmount(BigDecimal maxAmount, BigDecimal minAmount, BigDecimal suggestedAmount) {
    }
}
