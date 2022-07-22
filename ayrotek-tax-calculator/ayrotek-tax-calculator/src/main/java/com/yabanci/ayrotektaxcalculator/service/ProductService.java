package com.yabanci.ayrotektaxcalculator.service;

import com.yabanci.ayrotektaxcalculator.model.Product;
import com.yabanci.ayrotektaxcalculator.model.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final TaxService taxService;

    public List<Product> updateTax(ProductType productType) {
        Product[] productList = restTemplate.getForObject("http://localhost:8081/api/v1/products/"+productType, Product[].class);
        for (Product pr : productList) {
            BigDecimal taxIncludePrice = taxService.calculateTax(pr.getTaxFreePrice(), productType);
            pr.setTaxIncludedPrice(taxIncludePrice);
            restTemplate.put("http://localhost:8081/api/v1/products/"+pr.getId(), pr);
            restTemplate.put("http://localhost:8081/api/v1/products/"+pr.getId(), pr);
        }
        return List.of(productList);
    }
}
