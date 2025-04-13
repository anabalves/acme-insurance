package com.acmeinsurance.infrastructure.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record CustomerResponse(

        @Schema(description = "Customer document number", example = "36205578900") String documentNumber,

        @Schema(description = "Customer name", example = "John Wick") String name,

        @Schema(description = "Customer type", example = "NATURAL") String type,

        @Schema(description = "Customer gender", example = "MALE") String gender,

        @Schema(description = "Birth date", example = "1973-05-02") LocalDate dateOfBirth,

        @Schema(description = "Customer email", example = "johnwick@gmail.com") String email,

        @Schema(description = "Customer phone number", example = "11950503030") Long phoneNumber) {
}
