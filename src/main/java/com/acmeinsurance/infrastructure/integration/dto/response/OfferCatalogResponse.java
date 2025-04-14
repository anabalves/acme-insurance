package com.acmeinsurance.infrastructure.integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record OfferCatalogResponse(UUID id, @JsonProperty("product_id") UUID productId, String name,
        @JsonProperty("created_at") LocalDateTime createdAt, Boolean active, Map<String, BigDecimal> coverages,
        List<String> assistances,
        @JsonProperty("monthly_premium_amount") MonthlyPremiumAmount monthlyPremiumAmount) implements Serializable {
    public record MonthlyPremiumAmount(@JsonProperty("max_amount") BigDecimal maxAmount,
            @JsonProperty("min_amount") BigDecimal minAmount,
            @JsonProperty("suggested_amount") BigDecimal suggestedAmount) implements Serializable {
    }
}
