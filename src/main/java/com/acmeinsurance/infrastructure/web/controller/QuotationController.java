package com.acmeinsurance.infrastructure.web.controller;

import com.acmeinsurance.application.usecase.CreateQuotationUseCase;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;
import com.acmeinsurance.infrastructure.web.mapper.QuotationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quotations")
@Tag(name = "Quotations", description = "Endpoints for managing insurance quotations")
public class QuotationController {

    private final CreateQuotationUseCase createQuotationUseCase;
    private final QuotationMapper quotationMapper;

    public QuotationController(CreateQuotationUseCase createQuotationUseCase, QuotationMapper quotationMapper) {
        this.createQuotationUseCase = createQuotationUseCase;
        this.quotationMapper = quotationMapper;
    }

    @Operation(summary = "Create a new insurance quotation", description = "Validates the quotation request, verifies the product and offer via external catalog, caches the result, and stores the quotation if valid.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Quotation successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(ref = "#/components/schemas/ErrorResponse"))),
            @ApiResponse(responseCode = "422", description = "Business rule violation", content = @Content(schema = @Schema(ref = "#/components/schemas/ErrorResponse"))),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(ref = "#/components/schemas/ErrorResponse")))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuotationResponse> create(@Valid @RequestBody QuotationRequest request) {
        var quotation = quotationMapper.toDomain(request);
        var saved = createQuotationUseCase.execute(quotation);
        var response = quotationMapper.toDto(saved);
        return ResponseEntity.status(201).body(response);
    }
}
