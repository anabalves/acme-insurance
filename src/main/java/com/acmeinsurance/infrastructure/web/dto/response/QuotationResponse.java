package com.acmeinsurance.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record QuotationResponse(

        @Schema(description = "Internal quotation ID", example = "22345") Long id,

        @Schema(description = "Insurance policy ID if issued", example = "756969") Long insurancePolicyId,

        @Schema(description = "Product UUID", example = "1b2da7cc-b367-4196-8a78-9cfeec21f587") UUID productId,

        @Schema(description = "Offer UUID", example = "adc56d77-348c-4bf0-908f-22d402ee715c") UUID offerId,

        @Schema(description = "Insurance category", example = "HOME") String category,

        @Schema(description = "Creation timestamp", example = "2024-05-22T20:37:17.090098") LocalDateTime createdAt,

        @Schema(description = "Last update timestamp", example = "2024-05-22T21:05:02.090098") LocalDateTime updatedAt,

        @Schema(description = "Total monthly premium", example = "75.25") BigDecimal totalMonthlyPremiumAmount,

        @Schema(description = "Total coverage amount", example = "825000.00") BigDecimal totalCoverageAmount,

        @Schema(description = "Map of coverage name to value", example = "{\"Incêndio\": 250000.00, \"Desastres naturais\": 500000.00, \"Responsabilidade civil\": 75000.00}") Map<String, BigDecimal> coverages,

        @Schema(description = "List of assistances", example = "[\"Encanador\", \"Eletricista\", \"Chaveiro 24h\"]") Set<String> assistances,

        @Schema(description = "Customer info") CustomerResponse customer

) {
}
