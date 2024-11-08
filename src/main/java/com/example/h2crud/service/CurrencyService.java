package com.example.h2crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.h2crud.model.CurrencyInfo;
import com.example.h2crud.repo.CurrencyRepo;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepo currencyRepo;

    public List<CurrencyInfo> getAllCurrencies() {
        return currencyRepo.findAll();
    }    
}
