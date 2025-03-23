package com.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDto {

    private Long id;

    private Long listing;

    private Long user;

    @NotNull(message = "Please enter a price.")
    @Positive(message = "Invalid price. Value should be positive.")
    private double paymentAmount;

    private Date startDate;

    private Date endDate;
}
