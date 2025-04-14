package com.acmeinsurance.infrastructure.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CustomerRequest(

        @NotBlank(message = "Document number is required") @Schema(description = "Customer document number", example = "36205578900") String documentNumber,

        @NotBlank(message = "Name is required") @Schema(description = "Customer full name", example = "John Wick") String name,

        @NotBlank(message = "Customer type is required. Allowed values: NATURAL, LEGAL") @Pattern(regexp = "NATURAL|LEGAL", message = "Customer type must be either NATURAL or LEGAL") @Schema(description = "Customer type", example = "NATURAL", allowableValues = {
                "NATURAL", "LEGAL"}) String type,

        @NotBlank(message = "Gender is required. Allowed values: MALE, FEMALE, OTHER") @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be either MALE, FEMALE, or OTHER") @Schema(description = "Gender", example = "MALE", allowableValues = {
                "MALE", "FEMALE", "OTHER"}) String gender,

        @NotNull(message = "Date of birth is required") @Schema(description = "Date of birth (format: yyyy-MM-dd)", example = "1973-05-02", type = "string", format = "date") LocalDate dateOfBirth,

        @NotBlank(message = "Email is required") @Email(message = "Email format is invalid") @Schema(description = "Email address", example = "johnwick@gmail.com") String email,

        @NotNull(message = "Phone number is required") @Schema(description = "Phone number", example = "11950503030") Long phoneNumber

    ){
}
