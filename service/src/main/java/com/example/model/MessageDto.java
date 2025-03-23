package com.example.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;

    private Long sender;

    @NotNull(message = "Invalid ID. Please enter correct ID")
    @Positive(message = "ID must be a positive number")
    private Long receiver;

    @Size(max = 500, min = 3, message = "Invalid content. Size should be between 3 to 500.")
    @NotEmpty(message = "Please enter a content")
    private String content;

    private Date sentAt;
}
