package com.example.h2crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.h2crud.model.CurrencyInfo;
import com.example.h2crud.model.dto.CurrencyRequest;
import com.example.h2crud.repo.CurrencyRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyRepo currencyRepo;

    @GetMapping
    public ResponseEntity<?> getAllCurrencies() {
        List<CurrencyInfo> currencies = currencyRepo.findAll();
        if (currencies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<CurrencyInfo>>(currencies, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        String code = currencyRequest.getCode().toUpperCase();
        Optional<CurrencyInfo> existingCurrency = currencyRepo.findByCode(code);
        if (existingCurrency.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            CurrencyInfo newCurrency = new CurrencyInfo().setCode(code).setChineseName(currencyRequest.getName());
            currencyRepo.save(newCurrency);
            return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrencyByCode(@PathVariable Long id) {
        Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
        if (existingCurrency.isPresent()) {
            return new ResponseEntity<>(existingCurrency.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurrencyByCode(@PathVariable Long id, @Valid @RequestBody CurrencyRequest currencyRequest) {
        Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
        if (existingCurrency.isPresent()) {
            CurrencyInfo updatedCurrencyInfo = existingCurrency.get();
            updatedCurrencyInfo.setChineseName(currencyRequest.getName());
            currencyRepo.save(updatedCurrencyInfo);
            return new ResponseEntity<>(updatedCurrencyInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrencyByCode(@PathVariable Long id) {
        Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
        if (existingCurrency.isPresent()) {
            currencyRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
