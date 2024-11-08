package com.example.h2crud.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CurrencyRequest {
    @NotNull(message = "Currency name cannot be null")
    String name;
}
