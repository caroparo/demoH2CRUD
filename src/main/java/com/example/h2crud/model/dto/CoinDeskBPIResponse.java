package com.example.h2crud.model.dto;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoinDeskBPIResponse {
    private Time time;
    private Map<String, BpiCurrency> bpi;

    @Data
    @Accessors(chain = true)
    public static class Time {
        private String updatedISO;
    }

    @Data
    public static class BpiCurrency {
        private String code;
        private String rate;
    }
}
