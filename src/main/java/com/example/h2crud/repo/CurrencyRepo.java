package com.example.h2crud.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.h2crud.model.CurrencyInfo;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyInfo, Long> {
    @Query("SELECT c FROM CurrencyInfo c WHERE c.code = :code")
    Optional<CurrencyInfo> findByCode(@Param("code") String code);
}
