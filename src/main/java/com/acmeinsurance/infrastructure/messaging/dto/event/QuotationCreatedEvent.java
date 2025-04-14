package com.acmeinsurance.infrastructure.messaging.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record QuotationCreatedEvent(Long quotationId, UUID productId, UUID offerId, String category,
        BigDecimal totalMonthlyPremiumAmount, BigDecimal totalCoverageAmount, Map<String, BigDecimal> coverages,
        Set<String> assistances, String customerDocument, String customerName, String customerEmail,
        LocalDateTime createdAt) {
}
