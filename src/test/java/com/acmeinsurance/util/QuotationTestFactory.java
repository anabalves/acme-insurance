package com.acmeinsurance.util;

import com.acmeinsurance.domain.entity.*;
import com.acmeinsurance.domain.enums.Category;
import com.acmeinsurance.domain.enums.CustomerType;
import com.acmeinsurance.domain.enums.Gender;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import com.acmeinsurance.infrastructure.messaging.dto.event.QuotationCreatedEvent;
import com.acmeinsurance.infrastructure.persistence.entity.QuotationEntity;
import com.acmeinsurance.infrastructure.persistence.entity.embeddable.CustomerEmbeddable;
import com.acmeinsurance.infrastructure.web.dto.request.CustomerRequest;
import com.acmeinsurance.infrastructure.web.dto.request.QuotationRequest;
import com.acmeinsurance.infrastructure.web.dto.response.CustomerResponse;
import com.acmeinsurance.infrastructure.web.dto.response.QuotationResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class QuotationTestFactory {

    public static final UUID PRODUCT_ID = UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587");
    public static final UUID OFFER_ID = UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c");

    public static LocalDateTime now() {
        return LocalDateTime.of(2024, 4, 15, 12, 0, 0);
    }

    public static Quotation validQuotation() {
        return new Quotation(1L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("75.25"),
                new BigDecimal("825000.00"), validCoverages(), validAssistances(), validCustomer(), now(), now(), null);
    }

    public static Quotation validQuotationWithCustomerNull() {
        Quotation quotation = validQuotation();
        return new Quotation(quotation.id(), quotation.productId(), quotation.offerId(), quotation.category(),
                quotation.totalMonthlyPremiumAmount(), quotation.totalCoverageAmount(), quotation.coverages(),
                quotation.assistances(), null, quotation.createdAt(), quotation.updatedAt(),
                quotation.insurancePolicyId());
    }

    public static Quotation quotationWithNullFieldsAndPartialCustomer() {
        Customer partialCustomer = new Customer(null, null, CustomerType.NATURAL, Gender.MALE, LocalDate.of(1973, 5, 2),
                null, 11950503030L);

        return new Quotation(1L, PRODUCT_ID, OFFER_ID, null, new BigDecimal("75.25"), new BigDecimal("825000.00"), null,
                null, partialCustomer, now(), now(), null);
    }

    public static Quotation quotationWithNullFields() {
        return new Quotation(1L, PRODUCT_ID, OFFER_ID, null, new BigDecimal("75.25"), new BigDecimal("825000.00"), null,
                null, validCustomer(), now(), now(), null);
    }

    public static QuotationRequest validQuotationRequest() {
        return new QuotationRequest(PRODUCT_ID, OFFER_ID, "HOME", new BigDecimal("75.25"), new BigDecimal("825000.00"),
                validCoverages(), List.copyOf(validAssistances()), validCustomerRequest());
    }

    public static QuotationRequest quotationRequestWithNullFields() {
        return new QuotationRequest(PRODUCT_ID, OFFER_ID, null, new BigDecimal("75.25"), new BigDecimal("825000.00"),
                null, null, validCustomerRequest());
    }

    public static Product validProduct() {
        return new Product(PRODUCT_ID, "Seguro de Vida", now(), true, List.of(OFFER_ID));
    }

    public static Product inactiveProduct() {
        return new Product(PRODUCT_ID, "Product Inactive", now(), false, List.of(OFFER_ID));
    }

    public static Product validProductWithDifferentOffer() {
        return new Product(PRODUCT_ID, "Product with a different offer", now(), true, List.of(UUID.randomUUID()));
    }

    public static Offer validOffer() {
        return new Offer(OFFER_ID, PRODUCT_ID, "Seguro de Vida Familiar", now(), true, validCatalogCoverages(),
                List.of("Encanador", "Eletricista", "Chaveiro 24h", "Assistência Funerária"),
                new Offer.MonthlyPremiumAmount(new BigDecimal("100.74"), new BigDecimal("50.00"),
                        new BigDecimal("60.25")));
    }

    public static Quotation quotationWithExceedingCoverage() {
        Map<String, BigDecimal> coverages = new LinkedHashMap<>(validCoverages());
        coverages.put("Incêndio", new BigDecimal("1000000.00"));

        return new Quotation(10L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("75.25"),
                new BigDecimal("825000.00"), coverages, validAssistances(), validCustomer(), now(), now(), null);
    }

    public static Quotation quotationWithInvalidCoverage() {
        Map<String, BigDecimal> invalidCoverages = new LinkedHashMap<>();
        invalidCoverages.put("Cobertura Inexistente", new BigDecimal("100000.00"));
        return new Quotation(2L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("75.25"),
                new BigDecimal("825000.00"), invalidCoverages, validAssistances(), validCustomer(), now(), now(), null);
    }

    public static Quotation quotationWithInvalidAssistance() {
        Set<String> assistances = new HashSet<>(validAssistances());
        assistances.add("Pintor");
        return new Quotation(3L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("75.25"),
                new BigDecimal("825000.00"), validCoverages(), assistances, validCustomer(), now(), now(), null);
    }

    public static Quotation quotationWithPremiumBelowMinimum() {
        return new Quotation(4L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("10.00"),
                new BigDecimal("825000.00"), validCoverages(), validAssistances(), validCustomer(), now(), now(), null);
    }

    public static Quotation quotationWithPremiumAboveMaximum() {
        return new Quotation(5L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("150.00"),
                new BigDecimal("825000.00"), validCoverages(), validAssistances(), validCustomer(), now(), now(), null);
    }

    public static Quotation quotationWithWrongTotalCoverage() {
        return new Quotation(6L, PRODUCT_ID, OFFER_ID, Category.HOME, new BigDecimal("75.25"),
                new BigDecimal("999999.00"), validCoverages(), validAssistances(), validCustomer(), now(), now(), null);
    }

    public static PolicyIssuedEvent validPolicyIssuedEvent() {
        return new PolicyIssuedEvent(1L, 999L, now());
    }

    public static PolicyIssued validPolicyIssued() {
        return new PolicyIssued(1L, 999L, now());
    }

    public static QuotationCreatedEvent validQuotationCreatedEvent() {
        Quotation quotation = validQuotation();
        return new QuotationCreatedEvent(quotation.id(), quotation.productId(), quotation.offerId(),
                quotation.category().name(), quotation.totalMonthlyPremiumAmount(), quotation.totalCoverageAmount(),
                quotation.coverages(), quotation.assistances(), quotation.customer().documentNumber(),
                quotation.customer().name(), quotation.customer().email(), quotation.createdAt());
    }

    public static Map<String, BigDecimal> validCoverages() {
        Map<String, BigDecimal> coverages = new LinkedHashMap<>();
        coverages.put("Incêndio", new BigDecimal("250000.00"));
        coverages.put("Desastres naturais", new BigDecimal("500000.00"));
        coverages.put("Responsabiliadade civil", new BigDecimal("75000.00"));
        return coverages;
    }

    public static Map<String, BigDecimal> validCatalogCoverages() {
        Map<String, BigDecimal> coverages = new LinkedHashMap<>();
        coverages.put("Incêndio", new BigDecimal("500000.00"));
        coverages.put("Desastres naturais", new BigDecimal("600000.00"));
        coverages.put("Responsabiliadade civil", new BigDecimal("80000.00"));
        coverages.put("Roubo", new BigDecimal("100000.00"));
        return coverages;
    }

    public static Set<String> validAssistances() {
        return Set.of("Encanador", "Eletricista", "Chaveiro 24h");
    }

    public static Customer validCustomer() {
        return new Customer("36205578900", "John Wick", CustomerType.NATURAL, Gender.MALE, LocalDate.of(1973, 5, 2),
                "johnwick@gmail.com", 11950503030L);
    }

    public static CustomerRequest validCustomerRequest() {
        return new CustomerRequest("36205578900", "John Wick", "NATURAL", "MALE", LocalDate.of(1973, 5, 2),
                "johnwick@gmail.com", 11950503030L);
    }

    public static CustomerRequest customerRequestWithNullTypeAndGender() {
        return new CustomerRequest("36205578900", "John Wick", null, null, LocalDate.of(1973, 5, 2),
                "johnwick@gmail.com", 11950503030L);
    }

    public static Customer customerWithNullTypeAndGender() {
        return new Customer("36205578900", "John Wick", null, null, LocalDate.of(1973, 5, 2), "johnwick@gmail.com",
                11950503030L);
    }

    public static UUID validProductId() {
        return PRODUCT_ID;
    }

    public static UUID validOfferId() {
        return OFFER_ID;
    }

    public static OfferCatalogResponse validOfferCatalogResponse() {
        return new OfferCatalogResponse(OFFER_ID, PRODUCT_ID, "Seguro de Vida Familiar", now(), true,
                validCatalogCoverages(), List.of("Encanador", "Eletricista", "Chaveiro 24h", "Assistência Funerária"),
                new OfferCatalogResponse.MonthlyPremiumAmount(new BigDecimal("100.74"), new BigDecimal("50.00"),
                        new BigDecimal("60.25")));
    }

    public static OfferCatalogResponse validOfferCatalogResponseWithNullFields() {
        return new OfferCatalogResponse(QuotationTestFactory.validOfferId(), QuotationTestFactory.validProductId(),
                "Offer with null fields", QuotationTestFactory.now(), true, null, null, null);
    }

    public static ProductCatalogResponse validProductCatalogResponse() {
        return new ProductCatalogResponse(PRODUCT_ID, "Seguro de Vida", now(), true, List.of(OFFER_ID));
    }

    public static ProductCatalogResponse productCatalogResponseWithNullActive() {
        return new ProductCatalogResponse(PRODUCT_ID, "Product with active null", now(), null, List.of(OFFER_ID));
    }

    public static ProductCatalogResponse productCatalogResponseWithNullOffers() {
        return new ProductCatalogResponse(PRODUCT_ID, "Product with offers null", now(), true, null);
    }

    public static ProductCatalogResponse productCatalogResponseWithNullActiveAndOffers() {
        return new ProductCatalogResponse(PRODUCT_ID, "Product with active and offers null", now(), null, null);
    }

    public static QuotationEntity validQuotationEntity() {
        QuotationEntity entity = new QuotationEntity();
        entity.setId(1L);
        entity.setProductId(PRODUCT_ID);
        entity.setOfferId(OFFER_ID);
        entity.setCategory("HOME");
        entity.setTotalMonthlyPremiumAmount(new BigDecimal("75.25"));
        entity.setTotalCoverageAmount(new BigDecimal("825000.00"));
        entity.setCoverages(validCoverages());
        entity.setAssistances(validAssistances());
        entity.setCustomer(validCustomerEmbeddable());
        entity.setCreatedAt(now());
        entity.setUpdatedAt(now());
        entity.setInsurancePolicyId(null);
        return entity;
    }

    public static CustomerEmbeddable validCustomerEmbeddable() {
        CustomerEmbeddable embeddable = new CustomerEmbeddable();
        embeddable.setDocumentNumber("36205578900");
        embeddable.setName("John Wick");
        embeddable.setType("NATURAL");
        embeddable.setGender("MALE");
        embeddable.setDateOfBirth(LocalDate.of(1973, 5, 2));
        embeddable.setEmail("johnwick@gmail.com");
        embeddable.setPhoneNumber(11950503030L);
        return embeddable;
    }

    public static QuotationResponse validQuotationResponse() {
        return new QuotationResponse(1L, null, PRODUCT_ID, OFFER_ID, "HOME", now(), now(), new BigDecimal("75.25"),
                new BigDecimal("825000.00"), validCoverages(), validAssistances(), validCustomerResponse());
    }

    public static CustomerResponse validCustomerResponse() {
        return new CustomerResponse("36205578900", "John Wick", "NATURAL", "MALE", LocalDate.of(1973, 5, 2),
                "johnwick@gmail.com", 11950503030L);
    }
}
