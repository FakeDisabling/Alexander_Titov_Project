package com.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private Long id;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long user;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long reviewer;

    @NotNull(message = "Please enter the rating")
    @Range(min = 1, max = 5, message = "Invalid range. Range should be within 1 to 5.")
    private int rating;
}
