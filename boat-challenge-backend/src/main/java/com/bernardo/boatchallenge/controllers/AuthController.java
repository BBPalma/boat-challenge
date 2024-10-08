package com.bernardo.boatchallenge.controllers;

import com.bernardo.boatchallenge.components.JwtUtil;
import com.bernardo.boatchallenge.dto.AuthRequest;
import com.bernardo.boatchallenge.dto.AuthResponse;
import com.bernardo.boatchallenge.entities.User;
import com.bernardo.boatchallenge.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody User user) {
        return userDetailsService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(authRequest.getUsername());

        // Return the JWT token in the response
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
