package com.yabanci.ayrotekproductuser.dto;

import com.yabanci.ayrotekproductuser.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private int id;
    private String name;
    private ProductType productType;
    private BigDecimal taxFreePrice;
    private BigDecimal taxIncludedPrice;
    private UserDto user;
}
