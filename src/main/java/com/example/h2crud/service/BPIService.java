package com.example.h2crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.h2crud.model.dto.CoinDeskBPIResponse;

@Service
public class BPIService {
    private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private RestTemplate restTemplate;

    public CoinDeskBPIResponse getCurrentBPI() {
        String uriString = UriComponentsBuilder.fromHttpUrl(COINDESK_API_URL).toUriString();
        CoinDeskBPIResponse response = restTemplate.getForObject(uriString, CoinDeskBPIResponse.class);

        if (response != null && response.getBpi() != null) {
            return response;
        }

        throw new RuntimeException("Error fetching BPI data from CoinDesk.");
    }
}
