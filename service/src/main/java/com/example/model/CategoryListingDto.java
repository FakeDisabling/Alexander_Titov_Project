package com.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListingDto {

    private Long id;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long listing;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long category;
}
