package com.yabanci.ayrotektaxcalculator.service;

import com.yabanci.ayrotektaxcalculator.converter.TaxMapper;
import com.yabanci.ayrotektaxcalculator.dto.TaxDto;
import com.yabanci.ayrotektaxcalculator.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotektaxcalculator.dto.request.TaxUpdateRequestDto;
import com.yabanci.ayrotektaxcalculator.enums.VatErrorMessage;
import com.yabanci.ayrotektaxcalculator.exception.ItemNotFoundException;
import com.yabanci.ayrotektaxcalculator.model.ProductType;
import com.yabanci.ayrotektaxcalculator.model.Tax;
import com.yabanci.ayrotektaxcalculator.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private ProductService productService;

    @Autowired
    public void setProductService(@Lazy ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal calculateTax(BigDecimal price, ProductType productType){
        BigDecimal taxIncludedPrice = price.
                multiply(findByProductType(productType).getRate()).
                add(price);
        return taxIncludedPrice;
    }

    public List<TaxDto> findAll(){
        List<Tax> taxes = taxRepository.findAll();
        List<TaxDto> taxDtoList = TaxMapper.INSTANCE.convertToTaxDtoList(taxes);
        return taxDtoList;
    }
    public TaxDto findByProductType(ProductType productType){
        Tax tax = taxRepository.findByProductType(productType);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }
    public TaxDto save(TaxSaveRequestDto taxSaveRequestDto){
        Tax tax = TaxMapper.INSTANCE.convertToTax(taxSaveRequestDto);
        tax = taxRepository.save(tax);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }

    public TaxDto update(TaxUpdateRequestDto taxUpdateRequestDto){
        Tax tax = findById(taxUpdateRequestDto.getId());
        productService.updateTax(taxUpdateRequestDto.getProductType());
        tax.setProductType(taxUpdateRequestDto.getProductType());
        tax.setRate(taxUpdateRequestDto.getRate());
        tax = taxRepository.save(tax);
        TaxDto taxDto = TaxMapper.INSTANCE.convertToTaxDto(tax);
        return taxDto;
    }

    public void delete(long id){
        Tax tax = findById(id);
        taxRepository.delete(tax);
    }

    protected Tax findById(long id){
        Tax tax = taxRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(VatErrorMessage.VAT_NOT_FOUND));
        return tax;
    }
}
