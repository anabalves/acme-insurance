package com.acmeinsurance.domain.exception;

public class BusinessException extends RuntimeException {
    private final Integer statusCode;

    public BusinessException(String message) {
        super(message);
        this.statusCode = 422;
    }

    public BusinessException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
