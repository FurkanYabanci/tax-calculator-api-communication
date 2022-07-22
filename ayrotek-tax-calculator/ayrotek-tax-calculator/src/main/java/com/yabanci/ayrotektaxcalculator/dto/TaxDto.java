package com.yabanci.ayrotektaxcalculator.dto;

import com.yabanci.ayrotektaxcalculator.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class TaxDto {

    private Long id;
    private ProductType productType;
    private BigDecimal rate;

}
