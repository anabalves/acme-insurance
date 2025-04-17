package com.acmeinsurance.unit.domain.exception;

import com.acmeinsurance.domain.exception.GatewayException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // when
        GatewayException exception = new GatewayException("Service unavailable");

        // then
        assertThat(exception.getMessage()).isEqualTo("Service unavailable");
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        // given
        Throwable cause = new RuntimeException("Timeout");

        // when
        GatewayException exception = new GatewayException("Service unavailable", cause);

        // then
        assertThat(exception.getMessage()).isEqualTo("Service unavailable");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
