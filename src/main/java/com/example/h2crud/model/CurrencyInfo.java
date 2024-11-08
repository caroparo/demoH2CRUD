package com.example.h2crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Table(name="Currencies")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyInfo {
    private String code;
    private String chineseName;
}
