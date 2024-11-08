package com.example.h2crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.h2crud.model.vo.CoinDeskBPIResponseVO;

@Service
public class BPIService {
    private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private RestTemplate restTemplate;

    public CoinDeskBPIResponseVO getCurrentBPI() {
        String uriString = UriComponentsBuilder.fromHttpUrl(COINDESK_API_URL).toUriString();
        CoinDeskBPIResponseVO response = restTemplate.getForObject(uriString, CoinDeskBPIResponseVO.class);

        if (response != null && response.getBpi() != null) {
            return response;
        }

        throw new RuntimeException("Error fetching BPI data from CoinDesk.");
    }
}
