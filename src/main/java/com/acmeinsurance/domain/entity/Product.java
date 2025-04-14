package com.acmeinsurance.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Product(UUID id, String name, LocalDateTime createdAt, boolean active, List<UUID> offers) {
}
