package com.acmeinsurance.infrastructure.persistence.entity;

import com.acmeinsurance.infrastructure.persistence.entity.embeddable.CustomerEmbeddable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "quotations")
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID productId;
    private UUID offerId;
    private String category;
    private BigDecimal totalMonthlyPremiumAmount;
    private BigDecimal totalCoverageAmount;

    @ElementCollection
    @CollectionTable(name = "quotation_coverages", joinColumns = @JoinColumn(name = "quotation_id"))
    @MapKeyColumn(name = "coverage_name")
    @Column(name = "coverage_value")
    private Map<String, BigDecimal> coverages;

    @ElementCollection
    @CollectionTable(name = "quotation_assistances", joinColumns = @JoinColumn(name = "quotation_id"))
    @Column(name = "assistance")
    private Set<String> assistances;

    @Embedded
    private CustomerEmbeddable customer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long insurancePolicyId;

    public QuotationEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalMonthlyPremiumAmount() {
        return totalMonthlyPremiumAmount;
    }

    public void setTotalMonthlyPremiumAmount(BigDecimal totalMonthlyPremiumAmount) {
        this.totalMonthlyPremiumAmount = totalMonthlyPremiumAmount;
    }

    public BigDecimal getTotalCoverageAmount() {
        return totalCoverageAmount;
    }

    public void setTotalCoverageAmount(BigDecimal totalCoverageAmount) {
        this.totalCoverageAmount = totalCoverageAmount;
    }

    public Map<String, BigDecimal> getCoverages() {
        return coverages;
    }

    public void setCoverages(Map<String, BigDecimal> coverages) {
        this.coverages = coverages;
    }

    public Set<String> getAssistances() {
        return assistances;
    }

    public void setAssistances(Set<String> assistances) {
        this.assistances = assistances;
    }

    public CustomerEmbeddable getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEmbeddable customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getInsurancePolicyId() {
        return insurancePolicyId;
    }

    public void setInsurancePolicyId(Long insurancePolicyId) {
        this.insurancePolicyId = insurancePolicyId;
    }

}
