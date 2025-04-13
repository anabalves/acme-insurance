package com.acmeinsurance.domain.entity;

import java.time.LocalDate;

public record Customer(String documentNumber, String name, CustomerType type, Gender gender, LocalDate dateOfBirth,
        String email, Long phoneNumber) {
}
