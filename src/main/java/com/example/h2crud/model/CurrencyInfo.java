package com.example.h2crud.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="Currencies")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyInfo {
    @Id
    @Column(name = "code", length = 3, nullable = false, unique = true)
    private String code;

    @Column(name = "zh_tw", nullable = false)
    private String chineseName;
}
