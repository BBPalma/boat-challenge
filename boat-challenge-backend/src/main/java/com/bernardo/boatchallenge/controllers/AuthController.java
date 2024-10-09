package com.bernardo.boatchallenge.controllers;

import com.bernardo.boatchallenge.components.JwtUtil;
import com.bernardo.boatchallenge.dto.AuthRequest;
import com.bernardo.boatchallenge.dto.AuthResponse;
import com.bernardo.boatchallenge.dto.ErrorResponse;
import com.bernardo.boatchallenge.entities.User;
import com.bernardo.boatchallenge.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        try {
            log.info("Signup attempt for user: {}", user.getUsername());
            return ResponseEntity.ok(userDetailsService.addUser(user));
        } catch (IllegalArgumentException e) {
            log.error("Signup failed for user: {}; Reason: {}", user.getUsername(), e.getMessage());
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        log.info("Login attempt for user: {}", authRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(authRequest.getUsername());

            // Return the JWT token in the response
            log.info("Login successful for user: {}", authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (Exception e) {
            log.error("Login failed for user: {}; Reason: {}", authRequest.getUsername(), e.getMessage());
            return new ResponseEntity<>(new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED.value()),HttpStatus.UNAUTHORIZED);
        }
    }
}
