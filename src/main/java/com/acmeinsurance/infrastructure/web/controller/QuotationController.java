package com.acmeinsurance.infrastructure.web.controller;

import com.acmeinsurance.application.usecase.CreateQuotationUseCase;
import com.acmeinsurance.application.usecase.FindQuotationByIdUseCase;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;
import com.acmeinsurance.infrastructure.web.dto.common.ErrorResponse;
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
    private final FindQuotationByIdUseCase findQuotationByIdUseCase;
    private final QuotationMapper quotationMapper;

    public QuotationController(CreateQuotationUseCase createQuotationUseCase,
            FindQuotationByIdUseCase findQuotationByIdUseCase, QuotationMapper quotationMapper) {
        this.createQuotationUseCase = createQuotationUseCase;
        this.findQuotationByIdUseCase = findQuotationByIdUseCase;
        this.quotationMapper = quotationMapper;
    }

    @Operation(summary = "Create a new insurance quotation", description = "Validates the quotation request, verifies the product and offer via external catalog, caches the result, and stores the quotation if valid.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Quotation successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Business rule violation", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuotationResponse> create(@Valid @RequestBody QuotationRequest request) {
        var quotation = quotationMapper.toDomain(request);
        var saved = createQuotationUseCase.execute(quotation);
        var response = quotationMapper.toDto(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a quotation by ID", description = "Returns the quotation if it exists, including policy ID if applied")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Quotation found"),
            @ApiResponse(responseCode = "404", description = "Quotation not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<QuotationResponse> findById(@PathVariable Long id) {
        var quotation = findQuotationByIdUseCase.execute(id);
        var response = quotationMapper.toDto(quotation);
        return ResponseEntity.ok(response);
    }

}
