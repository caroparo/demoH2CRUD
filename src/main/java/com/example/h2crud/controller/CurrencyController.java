package com.example.h2crud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.h2crud.dto.CurrencyRequest;
import com.example.h2crud.model.CurrencyInfo;
import com.example.h2crud.repo.CurrencyRepo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CurrencyController {

    @Autowired
    private CurrencyRepo currencyRepo;

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyInfo>> getAllCurrencies() {
        try {
            List<CurrencyInfo> currencies = new ArrayList<CurrencyInfo>();
            currencyRepo.findAll().forEach(currencies::add);

            if (currencies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<CurrencyInfo>>(currencies, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/currencies/{code}")
    public ResponseEntity<CurrencyInfo> getCurrencyByCode(@PathVariable String code) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(code);
            if (existingCurrency.isPresent()) {
                return new ResponseEntity<>(existingCurrency.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/currencies/{code}")
    public ResponseEntity<CurrencyInfo> addCurrency(@PathVariable String code, @RequestBody CurrencyRequest currencyRequest) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(code);
            if (existingCurrency.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                CurrencyInfo newCurrency = new CurrencyInfo(code, currencyRequest.getName());
                currencyRepo.save(newCurrency);
                return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/currencies/{code}")
    public ResponseEntity<CurrencyInfo> updateCurrencyByCode(@PathVariable String code, @RequestBody CurrencyRequest currencyRequest) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(code);
            if (existingCurrency.isPresent()) {
                CurrencyInfo updatedCurrencyInfo = existingCurrency.get();
                updatedCurrencyInfo.setChineseName(currencyRequest.getName());
                currencyRepo.save(updatedCurrencyInfo);
                return new ResponseEntity<>(updatedCurrencyInfo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/currencies/{code}")
    public ResponseEntity<CurrencyInfo> deleteCurrencyByCode(@PathVariable String code) {
        try {
            Optional<CurrencyInfo> existingCurrency = currencyRepo.findById(code);
            if (existingCurrency.isPresent()) {
                currencyRepo.deleteById(code);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
