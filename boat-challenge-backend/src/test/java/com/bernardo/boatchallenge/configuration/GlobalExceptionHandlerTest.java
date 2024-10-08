package com.bernardo.boatchallenge.configuration;

import com.bernardo.boatchallenge.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleExpiredJwtException_BadCredentialsException_ReturnsUnauthorized() {
        BadCredentialsException ex = new BadCredentialsException("Invalid username or password.");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExpiredJwtException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password.", response.getBody().getMessage());
    }

    @Test
    void handleExpiredJwtException_UsernameNotFoundException_ReturnsUnauthorized() {
        UsernameNotFoundException ex = new UsernameNotFoundException("User not found.");
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleExpiredJwtException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Authentication failed.", response.getBody().getMessage());
    }

    @Test
    void handleValidationExceptions_MethodArgumentNotValidException_ReturnsBadRequest() throws JsonProcessingException {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(ex.getBindingResult().getAllErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"field\":\"defaultMessage\"}", response.getBody().getMessage());
    }

    @Test
    void handleDataIntegrityViolationException_UniqueIndexViolation_ReturnsBadRequest() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Unique index or primary key violation");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Duplicate object found", response.getBody().getMessage());
    }

    @Test
    void handleDataIntegrityViolationException_OtherException_ReturnsInternalServerError() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Some other exception");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Some other exception", response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_ReturnsInternalServerError() {
        Exception ex = new Exception("General error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: General error", response.getBody().getMessage());
    }
}