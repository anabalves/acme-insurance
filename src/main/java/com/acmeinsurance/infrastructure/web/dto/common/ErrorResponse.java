package com.acmeinsurance.infrastructure.web.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Error response returned by the API in case of failure")
public record ErrorResponse(

        @Schema(description = "Error code", example = "400") String code,

        @Schema(description = "Error message", example = "These attributes are missing or are invalid") String message,

        @Schema(description = "Optional list of validation errors") List<ValidationError> errors

) {

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, null);
    }

    public static ErrorResponse of(String code, String message, List<ValidationError> errors) {
        return new ErrorResponse(code, message, errors);
    }

    @Schema(description = "Validation error details")
    public record ValidationError(

            @Schema(description = "Name of the field with the error", example = "email") String field,

            @Schema(description = "Description of the validation issue", example = "Email format is invalid") String description

    ) {
    }

}
