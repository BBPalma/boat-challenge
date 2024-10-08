package com.bernardo.boatchallenge.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Table(name="`USER`") //Escaped user keyword for H2
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    @Column(unique = true, nullable = false, columnDefinition="VARCHAR_IGNORECASE(50)")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 12, message = "Password must have at least 12 characters")
    private String password;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(nullable = false)
    private String role = "ROLE_USER";
}