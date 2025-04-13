package com.acmeinsurance.domain.entity;

import java.time.LocalDateTime;

public record PolicyIssued(
        Long quotationId,
        Long policyId,
        LocalDateTime issuedAt
) {}
