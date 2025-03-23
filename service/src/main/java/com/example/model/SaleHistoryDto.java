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
public class SaleHistoryDto {

    private Long id;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long seller;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long buyer;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long listing;

    private Date saleDate;
}
