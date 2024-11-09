package com.example.h2crud.model.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class BPIResponse {
    private String timeUpdated;
    private Map<String, CurrencyDetails> bpi;

    @Data
    @AllArgsConstructor
    public static class CurrencyDetails {
        private String code;
        private String name;
        private String rate;
    }
}
