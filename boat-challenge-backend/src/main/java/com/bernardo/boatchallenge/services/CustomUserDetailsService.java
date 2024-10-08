package com.bernardo.boatchallenge.services;

import com.bernardo.boatchallenge.dto.CustomUserDetails;
import com.bernardo.boatchallenge.entities.User;
import com.bernardo.boatchallenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, @Lazy PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Converting UserInfo to UserDetails
        return new CustomUserDetails(user);
    }

    public String addUser(User user) {
        // Encode password before saving the user
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return "User Added Successfully";
    }
}
