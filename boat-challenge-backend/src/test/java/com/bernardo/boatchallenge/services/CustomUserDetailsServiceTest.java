package com.bernardo.boatchallenge.services;

import com.bernardo.boatchallenge.entities.User;
import com.bernardo.boatchallenge.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonexistentuser");
        });
    }

    @Test
    void addUser_ValidUser_ReturnsSuccessMessage() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("plainpassword");
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");

        String result = customUserDetailsService.addUser(user);

        assertEquals("User Added Successfully", result);
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedpassword", user.getPassword());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void addUser_UsernameAlreadyTaken_ThrowsException() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customUserDetailsService.addUser(user);
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void addUser_NullUsername_ThrowsException() {
        User user = new User();
        user.setUsername(null);
        user.setPassword("password");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customUserDetailsService.addUser(user);
        });

        assertEquals("Username and password are required", exception.getMessage());
    }

    @Test
    void addUser_EmptyUsername_ThrowsException() {
        User user = new User();
        user.setUsername("");
        user.setPassword("password");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customUserDetailsService.addUser(user);
        });

        assertEquals("Username and password are required", exception.getMessage());
    }

    @Test
    void addUser_NullPassword_ThrowsException() {
        User user = new User();
        user.setUsername("username");
        user.setPassword(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customUserDetailsService.addUser(user);
        });

        assertEquals("Username and password are required", exception.getMessage());
    }

    @Test
    void addUser_EmptyPassword_ThrowsException() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customUserDetailsService.addUser(user);
        });

        assertEquals("Username and password are required", exception.getMessage());
    }
}