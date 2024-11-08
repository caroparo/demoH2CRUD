package com.example.h2crud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.h2crud.model.CurrencyInfo;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyInfo, String> {
}
