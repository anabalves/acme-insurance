package com.acmeinsurance.infrastructure.messaging.dto.event;

import java.time.LocalDateTime;

public record PolicyIssuedEvent(
        Long quotationId,
        Long policyId,
        LocalDateTime issuedAt
) {}
