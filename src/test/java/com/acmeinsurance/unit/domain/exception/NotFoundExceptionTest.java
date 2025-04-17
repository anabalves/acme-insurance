package com.acmeinsurance.unit.domain.exception;

import com.acmeinsurance.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // when
        NotFoundException exception = new NotFoundException("Resource not found");

        // then
        assertThat(exception.getMessage()).isEqualTo("Resource not found");
        assertThat(exception.getCause()).isNull();
    }
}
