package com.acmeinsurance.unit.domain.exception;

import com.acmeinsurance.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultStatusCode() {
        // when
        BusinessException exception = new BusinessException("Something went wrong");

        // then
        assertThat(exception.getMessage()).isEqualTo("Something went wrong");
        assertThat(exception.getStatusCode()).isEqualTo(422);
    }

    @Test
    void shouldCreateExceptionWithCustomStatusCode() {
        // when
        BusinessException exception = new BusinessException("Unauthorized", 401);

        // then
        assertThat(exception.getMessage()).isEqualTo("Unauthorized");
        assertThat(exception.getStatusCode()).isEqualTo(401);
    }
}
