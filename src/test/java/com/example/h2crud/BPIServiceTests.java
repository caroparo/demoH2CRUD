package com.example.h2crud;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.h2crud.model.dto.CoinDeskBPIResponse;
import com.example.h2crud.service.BPIService;

@SpringBootTest
public class BPIServiceTests {

    @Autowired
    private BPIService bpiService;

    @Test
    void GET_CoinDeskBPI_Expected() {
        CoinDeskBPIResponse result = bpiService.getCurrentBPI();
        assertNotNull(result, "The response should not be null");
        assertNotNull(result.getBpi(), "The BPI data should not be null");
        assertTrue(result.getBpi().size() > 0, "The BPI data should contain valid information");
    }
}
