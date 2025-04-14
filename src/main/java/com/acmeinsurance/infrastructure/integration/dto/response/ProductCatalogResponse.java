package com.acmeinsurance.infrastructure.integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductCatalogResponse(UUID id, String name, @JsonProperty("created_at") LocalDateTime createdAt,
        Boolean active, List<UUID> offers) implements Serializable {
}
