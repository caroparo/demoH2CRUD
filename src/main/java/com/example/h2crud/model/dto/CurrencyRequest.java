package com.example.h2crud.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CurrencyRequest {
    @NotNull(message = "Currency name cannot be null")
    @Size(min = 1, max = 255, message = "Currency name must be between 1 to 255 characters long")
    String name;
}
