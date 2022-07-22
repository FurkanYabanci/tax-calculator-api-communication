package com.yabanci.ayrotektaxcalculator.service;


import com.yabanci.ayrotektaxcalculator.dto.TaxDto;
import com.yabanci.ayrotektaxcalculator.dto.request.TaxSaveRequestDto;
import com.yabanci.ayrotektaxcalculator.exception.ItemNotFoundException;
import com.yabanci.ayrotektaxcalculator.model.ProductType;
import com.yabanci.ayrotektaxcalculator.model.Tax;
import com.yabanci.ayrotektaxcalculator.repository.TaxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaxServiceTest {

    @Spy
    @InjectMocks
    private TaxService taxService;

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private ProductService productService;

    @Test
    void shouldFindAll() {
        Tax tax = mock(Tax.class);
        List<Tax> taxList = new ArrayList<>();
        taxList.add(tax);

        when(taxRepository.findAll()).thenReturn(taxList);

        List<TaxDto> result = taxService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<Tax> taxList = new ArrayList<>();

        when(taxRepository.findAll()).thenReturn(taxList);

        List<TaxDto> result = taxService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindByProductType() {
        Tax tax = mock(Tax.class);
        ProductType productType = ProductType.FOOD;

        when(tax.getProductType()).thenReturn(productType);
        when(taxRepository.findByProductType(productType)).thenReturn(tax);

        TaxDto result = taxService.findByProductType(productType);

        assertEquals(productType,result.getProductType());
    }

    @Test
    void shouldNotFindByProductTypeWhenProductTypeDoesNotExist() {
        when(taxRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> taxService.findById(anyLong()));
        verify(taxRepository).findById(anyLong());
    }

    @Test
    void shouldSave() {

        TaxSaveRequestDto taxSaveRequestDto = mock(TaxSaveRequestDto.class);
        Tax tax = mock(Tax.class);

        when(tax.getProductType()).thenReturn(ProductType.FOOD);
        when(taxRepository.save(any())).thenReturn(tax);

        TaxDto result = taxService.save(taxSaveRequestDto);

        assertEquals(result.getProductType(),ProductType.FOOD);
    }

    /*@Test
    void update() {
        TaxUpdateRequestDto taxUpdateRequestDto = mock(TaxUpdateRequestDto.class);
        Tax tax = mock(Tax.class);
        Product product = mock(Product.class);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        doReturn(tax).when(taxService).findById(anyLong());

        when(taxUpdateRequestDto.getProductType()).thenReturn(ProductType.FOOD);
        when(taxUpdateRequestDto.getRate()).thenReturn(BigDecimal.ONE);
       // when(productService.updateTax(any())).thenReturn(productList);
        when(tax.getProductType()).thenReturn(ProductType.FOOD);
        when(taxRepository.save(any())).thenReturn(tax);


        TaxDto result = taxService.update(taxUpdateRequestDto);

        assertEquals(result.getProductType(),ProductType.FOOD);
    }
*/
    @Test
    void shouldDelete() {
        Tax tax = mock(Tax.class);

        doReturn(tax).when(taxService).findById(anyLong());

        taxService.delete(anyLong());

        verify(taxRepository).delete(any());
        verify(taxService).findById(anyLong());
    }
    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(taxRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> taxService.delete(anyLong()));

        verify(taxRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        Tax tax = mock(Tax.class);

        when(taxRepository.findById(anyLong())).thenReturn(Optional.ofNullable(tax));

        Tax result = taxService.findById(anyLong());

        assertEquals(result.getId(), tax.getId());
    }
    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(taxRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> taxService.findById(anyLong()));
        verify(taxRepository).findById(anyLong());
    }
}