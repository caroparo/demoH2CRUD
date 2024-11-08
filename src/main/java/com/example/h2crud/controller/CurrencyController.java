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
        try {
            List<CurrencyInfo> currencies = new ArrayList<CurrencyInfo>(currencyRepo.findAll());
            if (currencies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<CurrencyInfo>>(currencies, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        try {
            String code = currencyRequest.getCode().toUpperCase();
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findByCode(code);
            if (existingCurrency.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                CurrencyInfo newCurrency = new CurrencyInfo().setCode(code).setChineseName(currencyRequest.getName());
                currencyRepo.save(newCurrency);
                return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrencyByCode(@PathVariable Long id) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
            if (existingCurrency.isPresent()) {
                return new ResponseEntity<>(existingCurrency.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurrencyByCode(@PathVariable Long id, @Valid @RequestBody CurrencyRequest currencyRequest) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
            if (existingCurrency.isPresent()) {
                CurrencyInfo updatedCurrencyInfo = existingCurrency.get();
                updatedCurrencyInfo.setChineseName(currencyRequest.getName());
                currencyRepo.save(updatedCurrencyInfo);
                return new ResponseEntity<>(updatedCurrencyInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrencyByCode(@PathVariable Long id) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(id);
            if (existingCurrency.isPresent()) {
                currencyRepo.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
