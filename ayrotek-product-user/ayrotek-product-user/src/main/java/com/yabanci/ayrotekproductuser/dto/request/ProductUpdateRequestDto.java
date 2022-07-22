package com.yabanci.ayrotekproductuser.dto.request;

import com.yabanci.ayrotekproductuser.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateRequestDto {

    private Long id;
    private String name;
    private ProductType productType;
    private BigDecimal taxFreePrice;
    private Long userId;
}