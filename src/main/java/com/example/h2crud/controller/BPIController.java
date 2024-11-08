package com.example.h2crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.h2crud.model.CurrencyInfo;
import com.example.h2crud.model.dto.BPIResponse;
import com.example.h2crud.model.vo.CoinDeskBPIResponseVO;
import com.example.h2crud.service.BPIService;
import com.example.h2crud.service.CurrencyService;

@RestController
@RequestMapping("/api")
public class BPIController {

    @Autowired
    private BPIService bpiService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public CoinDeskBPIResponseVO getCurrentBPI() {
        List<CurrencyInfo> currenciesToTrack = currencyService.getAllCurrencies();
        return bpiService.getCurrentBPI();
    }
}
