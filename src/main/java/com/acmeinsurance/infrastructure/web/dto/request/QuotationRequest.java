package com.acmeinsurance.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record QuotationRequest(

        @NotNull(message = "Product ID is required") @Schema(description = "Product UUID", example = "1b2da7cc-b367-4196-8a78-9cfeec21f587") UUID productId,

        @NotNull(message = "Offer ID is required") @Schema(description = "Offer UUID", example = "adc56d77-348c-4bf0-908f-22d402ee715c") UUID offerId,

        @NotNull(message = "Category is required. Allowed values: HOME, AUTO, LIFE") @Schema(description = "Category of the insurance", example = "HOME", allowableValues = {
                "HOME", "AUTO", "LIFE"}) String category,

        @NotNull(message = "Total monthly premium amount is required") @DecimalMin(value = "0.0", inclusive = false, message = "Total monthly premium must be greater than zero") @Schema(description = "Total monthly premium amount", example = "75.25") BigDecimal totalMonthlyPremiumAmount,

        @NotNull(message = "Total coverage amount is required") @DecimalMin(value = "0.0", inclusive = false, message = "Total coverage amount must be greater than zero") @Schema(description = "Total coverage amount", example = "825000.00") BigDecimal totalCoverageAmount,

        @NotEmpty(message = "At least one coverage must be provided") @Schema(description = "Map of coverage name to amount", example = "{\"Incêndio\": 250000.00, \"Desastres naturais\": 500000.00, \"Responsabilidade civil\": 75000.00}") Map<String, @DecimalMin(value = "0.0", inclusive = false, message = "Coverage amount must be greater than zero") BigDecimal> coverages,

        @NotEmpty(message = "At least one assistance must be provided") @Schema(description = "List of assistances", example = "[\"Encanador\", \"Eletricista\", \"Chaveiro 24h\"]") List<@NotBlank(message = "Assistance name cannot be blank") String> assistances,

        @NotNull(message = "Customer information is required") @Valid CustomerRequest customer){
}
