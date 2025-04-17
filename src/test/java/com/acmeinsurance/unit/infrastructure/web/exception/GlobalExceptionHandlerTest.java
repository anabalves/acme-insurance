package com.acmeinsurance.unit.infrastructure.web.exception;

import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.GatewayException;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.infrastructure.web.dto.common.ErrorResponse;
import com.acmeinsurance.infrastructure.web.exception.GlobalExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException mockArgumentNotValid = mock(MethodArgumentNotValidException.class);
        BindingResult mockBindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("quotationRequest", "productId", "must not be null");

        when(mockBindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(mockArgumentNotValid.getBindingResult()).thenReturn(mockBindingResult);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleValidation(mockArgumentNotValid);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().errors()).hasSize(1);
    }

    @Test
    void shouldHandleConstraintViolationException() {
        // given
        ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);
        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("field");
        when(mockViolation.getPropertyPath()).thenReturn(mockPath);
        when(mockViolation.getMessage()).thenReturn("must not be null");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(mockViolation));

        // when
        ResponseEntity<ErrorResponse> response = handler.handleConstraintViolation(ex);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().errors()).hasSize(1);
        assertThat(response.getBody().errors().get(0).field()).isEqualTo("field");
        assertThat(response.getBody().errors().get(0).description()).isEqualTo("must not be null");
    }

    @Test
    void shouldHandleMissingHeader() {
        MissingRequestHeaderException ex = new MissingRequestHeaderException("x-auth-token", null);
        ResponseEntity<ErrorResponse> response = handler.handleMissingHeader(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().message()).contains("Missing");
    }

    @Test
    void shouldHandleMissingServletRequestParameter() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("id", "Long");
        ResponseEntity<ErrorResponse> response = handler.handleMissingParameter(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().message()).contains("Missing");
    }

    @Test
    void shouldHandleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("abc", Integer.class, "id",
                null, new RuntimeException());
        ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().message()).contains("Invalid");
    }

    @Test
    void shouldHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Unreadable", new RuntimeException());
        ResponseEntity<ErrorResponse> response = handler.handleBadRequestBody(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody().message()).contains("Malformed");
    }

    @Test
    void shouldHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("Not found");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody().message()).isEqualTo("Not found");
    }

    @Test
    void shouldHandleBusinessException() {
        BusinessException ex = new BusinessException("Business error");
        ResponseEntity<ErrorResponse> response = handler.handleBusiness(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(422);
        assertThat(response.getBody().message()).isEqualTo("Business error");
    }

    @Test
    void shouldHandleGatewayException() {
        GatewayException ex = new GatewayException("Integration failed");
        ResponseEntity<ErrorResponse> response = handler.handleGateway(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(502);
        assertThat(response.getBody().message()).isEqualTo("External service is unavailable");
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected error");
        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody().message()).contains("unexpected");
    }
}
