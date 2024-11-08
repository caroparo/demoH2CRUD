package com.example.h2crud.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.h2crud.model.CurrencyInfo;
import com.example.h2crud.model.dto.BPIResponse;
import com.example.h2crud.model.dto.BPIResponse.CurrencyDetails;
import com.example.h2crud.model.dto.CoinDeskBPIResponse.BpiCurrency;
import com.example.h2crud.model.dto.CoinDeskBPIResponse;
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
    public BPIResponse getCurrentBPI() {
        List<CurrencyInfo> currenciesToTrack = currencyService.getAllCurrencies();
        CoinDeskBPIResponse bpi = bpiService.getCurrentBPI();
        Map<String, BpiCurrency> bpiMap = bpi.getBpi();

        // create translated CurrencyDetails
        Map<String, CurrencyDetails> currencyDetailsMap = currenciesToTrack.stream()
            .collect(Collectors.toMap(
                CurrencyInfo::getCode,
                currencyInfo -> {
                    String code = currencyInfo.getCode();
                    return new CurrencyDetails(
                        code, 
                        currencyInfo.getChineseName(), 
                        bpiMap.containsKey(code) ? bpiMap.get(code).getRate() : "not available"
                    );
                }
            ));
        
        // local time string formatter
        String isoString = bpi.getTime().getUpdatedISO();
        ZonedDateTime utcZonedDateTime = ZonedDateTime.parse(isoString);
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedTimeUpdatedString = localZonedDateTime.format(formatter);

        return new BPIResponse(formattedTimeUpdatedString, currencyDetailsMap);
    }
}
