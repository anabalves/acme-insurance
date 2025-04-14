package com.acmeinsurance.infrastructure.integration.feign.decoder;

import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.GatewayException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.warn("Checking response error at URL: {}", response.request().url());

        String responseBody = "";
        if (response.body() != null) {
            try {
                byte[] responseBytes = response.body().asInputStream().readAllBytes();
                responseBody = new String(responseBytes, StandardCharsets.UTF_8);
                response.body().close();
            } catch (IOException e) {
                log.warn("Was not possible to parse response body [{}]", e.getMessage());
            }
        }

        Exception exception = defaultDecoder.decode(methodKey, response);

        if (response.status() >= 400 && response.status() <= 499) {
            log.error("Client error. StatusCode [{}] | Response [{}]", response.status(), responseBody);
            exception = new BusinessException("A client error has occurred", response.status());
        } else if (response.status() >= 500) {
            log.error("Service unavailable. StatusCode [{}] | Response [{}]", response.status(), responseBody);
            exception = new GatewayException("A server error has occurred", exception);
        }

        return exception;
    }
}