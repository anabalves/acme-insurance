package com.acmeinsurance.unit.infrastructure.integration.feign.decoder;

import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.GatewayException;
import com.acmeinsurance.infrastructure.integration.feign.decoder.FeignErrorDecoder;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignErrorDecoderTest {

    private final Request request = Request.create(Request.HttpMethod.GET, "/home", Collections.emptyMap(),
            "data".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8, null);

    private final FeignErrorDecoder decoder = new FeignErrorDecoder();

    @Test
    void shouldReturnBusinessExceptionWhenStatusIs499() {
        // given
        Response response = Response.builder().status(499).body("response".getBytes(StandardCharsets.UTF_8))
                .request(request).build();

        // when
        Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(((BusinessException) exception).getStatusCode()).isEqualTo(499);
        assertThat(exception.getMessage()).contains("client error");
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 502, 599})
    void shouldReturnGatewayExceptionWhenStatusIsServerError(int httpStatus) {
        // given
        Response response = Response.builder().status(httpStatus).body("response".getBytes(StandardCharsets.UTF_8))
                .request(request).build();

        // when
        Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception).isInstanceOf(GatewayException.class);
        assertThat(exception.getMessage()).contains("server error");
    }

    @Test
    void shouldReturnDefaultExceptionWhenStatusIs200() {
        // given
        Response response = Response.builder().status(200).body("response".getBytes(StandardCharsets.UTF_8))
                .request(request).build();

        // when
        Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception).isNotNull();
        assertThat(exception).isNotInstanceOf(BusinessException.class);
        assertThat(exception).isNotInstanceOf(GatewayException.class);
    }

    @Test
    void shouldReturnBusinessExceptionWhenBodyIsEmpty() {
        // given
        Response response = Response.builder().status(400).request(request).build();

        // when
        Exception exception = decoder.decode("methodKey", response);

        // then
        assertThat(exception).isInstanceOf(BusinessException.class);
        assertThat(((BusinessException) exception).getStatusCode()).isEqualTo(400);
    }

    @Test
    void shouldNotThrowExceptionWhenBodyFailsToParse() throws IOException {
        // given
        var body = mock(Response.Body.class);
        var response = mock(Response.class);

        when(response.body()).thenReturn(body);
        when(response.request()).thenReturn(request);
        when(body.asInputStream()).thenThrow(IOException.class);

        // when & then
        assertDoesNotThrow(() -> decoder.decode("methodKey", response));
    }

}
