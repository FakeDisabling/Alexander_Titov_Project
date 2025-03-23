package com.example.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @Size(max = 20, min = 3, message = "Invalid name. Size should be between 3 to 20.")
    @NotEmpty(message = "Please enter your name.")
    private String username;

    @Email(message = "Invalid email address. Please enter a proper email ID.")
    @NotEmpty(message = "Please enter your email ID.")
    private String email;

    @Size(max = 15, min = 10, message = "Invalid phone. Size should be between 10 to 15.")
    @NotEmpty(message = "Please enter a phone")
    private String phone;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long role;

    @Size(max = 50, min = 3, message = "Invalid password. Size should be between 3 to 50.")
    @NotEmpty(message = "Please enter password")
    private String password;
}
