package com.yabanci.ayrotektaxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Product {

    private int id;
    private String name;
    private ProductType productType;
    private BigDecimal taxFreePrice;
    private BigDecimal taxIncludedPrice;
    private User user;
}
