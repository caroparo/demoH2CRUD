package com.example.h2crud.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Table(name="Currencies")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CurrencyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 3, columnDefinition = "VARCHAR(3)")
    private String code;

    @Column(name = "zh_tw", unique = true, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String chineseName;
}
