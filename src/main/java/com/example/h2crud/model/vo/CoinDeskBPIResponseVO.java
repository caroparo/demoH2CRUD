package com.example.h2crud.model.vo;

import java.util.Map;

import lombok.Data;

@Data
public class CoinDeskBPIResponseVO {
    private Time time;
    private Map<String, BpiCurrency> bpi;

    @Data
    public static class Time {
        private String updated;
    }

    @Data
    public static class BpiCurrency {
        private String code;
        private String rate;
    }
}
