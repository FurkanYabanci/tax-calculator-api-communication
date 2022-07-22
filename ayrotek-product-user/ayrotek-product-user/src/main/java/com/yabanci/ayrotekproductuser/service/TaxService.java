package com.yabanci.ayrotekproductuser.service;

import com.yabanci.ayrotekproductuser.model.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TaxService {
    private final RestTemplate restTemplate;
    public BigDecimal getCalculatedTax(BigDecimal price,ProductType productType){
        ResponseEntity<BigDecimal> taxIncludedPrice = restTemplate.getForEntity("http://localhost:8080/api/v1/taxes/"+price+"/"+productType ,BigDecimal.class);
        return taxIncludedPrice.getBody();
    }
}
