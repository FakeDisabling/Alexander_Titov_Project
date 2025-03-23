package com.example.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;

    @Size(max = 50, min = 3, message = "Invalid content. Size should be between 3 to 50.")
    @NotEmpty(message = "Please enter a role")
    private String name;
}
