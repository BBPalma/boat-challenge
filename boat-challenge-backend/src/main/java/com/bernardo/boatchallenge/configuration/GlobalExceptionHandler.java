package com.bernardo.boatchallenge.configuration;

import com.bernardo.boatchallenge.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle AuthExceptions
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(RuntimeException ex) {
        String message = "Authentication failed.";
        if (ex instanceof BadCredentialsException) {
            message = "Invalid username or password.";
        }
        log.error("Authentication error: {}", message, ex);
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) throws JsonProcessingException {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(
                objectMapper.writeValueAsString(errors),
                HttpStatus.BAD_REQUEST.value()
        );
        log.error("Validation error: {}", errors, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(Exception ex) {
        if (ex.getMessage().contains("Unique index")) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "Duplicate object found",
                    HttpStatus.BAD_REQUEST.value()
            );
            log.error("Data integrity violation: Duplicate object found", ex);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return handleGeneralException(ex);
    }

    // Handle other exceptions as needed
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "An error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        log.error("General error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
