package com.example.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    private Long id;

    private Long user;

    @Size(max = 50, min = 3, message = "Invalid title. Size should be between 3 to 50.")
    @NotEmpty(message = "Please enter the title.")
    private String title;

    @Size(max = 500, min = 3, message = "Invalid description. Size should be between 3 to 500.")
    @NotEmpty(message = "Please enter the title.")
    private String description;

    @NotNull(message = "Please enter a price.")
    @Positive(message = "Invalid price. Value should be positive.")
    private double price;

    private Date createdAt;
}
