package com.bernardo.boatchallenge.controllers;

import com.bernardo.boatchallenge.components.JwtUtil;
import com.bernardo.boatchallenge.dto.AuthRequest;
import com.bernardo.boatchallenge.dto.AuthResponse;
import com.bernardo.boatchallenge.dto.ErrorResponse;
import com.bernardo.boatchallenge.entities.User;
import com.bernardo.boatchallenge.services.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_ValidUser_ReturnsSuccessMessage() {
        User user = new User();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        when(userDetailsService.addUser(user)).thenReturn("User Added Successfully");

        ResponseEntity<?> result = authController.signUp(user);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("User Added Successfully", result.getBody());
    }

    @Test
    void signUp_DuplicateUser_ReturnsErrorMessage() {
        User user = new User();
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(new ErrorResponse("Username is already taken", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        when(userDetailsService.addUser(user)).thenThrow(new IllegalArgumentException("Username already exists"));

        ResponseEntity<?> result = authController.signUp(user);

        assertEquals("Username already exists", ((ErrorResponse) Objects.requireNonNull(result.getBody())).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void login_ValidCredentials_ReturnsJwtToken() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("testpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.generateToken("testuser")).thenReturn("jwt-token");

        ResponseEntity<?> response = authController.login(authRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwt-token", ((AuthResponse) response.getBody()).getJwt());
    }

    @Test
    void login_InvalidCredentials_ThrowsAuthenticationException() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("wrongpassword");

        doThrow(new AuthenticationException("Invalid credentials") {}).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(AuthenticationException.class, () -> {
            authController.login(authRequest);
        });
    }
}