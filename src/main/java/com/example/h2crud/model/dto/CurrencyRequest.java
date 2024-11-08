package com.example.h2crud.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CurrencyRequest {
    @NotNull(message = "The 'name' field must not be null.")
    @Size(min = 1, max = 255, message = "The 'name' field must be between 1 to 255 characters in length.")
    String name;
}
