package com.yabanci.ayrotekproductuser.service;

import com.yabanci.ayrotekproductuser.dto.ProductDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotekproductuser.exception.ItemNotFoundException;
import com.yabanci.ayrotekproductuser.model.Product;
import com.yabanci.ayrotekproductuser.model.ProductType;
import com.yabanci.ayrotekproductuser.model.User;
import com.yabanci.ayrotekproductuser.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
class ProductServiceTest {

    @Spy
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @Mock
    private TaxService taxService;


    @Test
    void shouldFindAll() {
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindAllByProductType() {

        ProductType productType = ProductType.FOOD;

        User user = mock(User.class);
        Product product = mock(Product.class);

        when(product.getProductType()).thenReturn(productType);
        when(product.getUser()).thenReturn(user);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAllByProductType(productType)).thenReturn(productList);

        List<ProductDto> result = productService.findAllByProductType(productType);

        assertEquals(productType,result.get(0).getProductType());
    }

    @Test
    void shouldSave() {
        ProductSaveRequestDto productSaveRequestDto = mock(ProductSaveRequestDto.class);
        Product product = mock(Product.class);
        User user = mock(User.class);

        when(productSaveRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(productSaveRequestDto.getProductType()).thenReturn(ProductType.FOOD);
        when(product.getName()).thenReturn("Bread");

        when(taxService.getCalculatedTax(BigDecimal.ONE,ProductType.FOOD)).thenReturn(BigDecimal.ONE);
        when(userService.findById(anyLong())).thenReturn(user);
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.save(productSaveRequestDto);

        assertEquals(result.getName(),product.getName());

        verify(userService).findById(anyLong());
        verify(taxService).getCalculatedTax(any(),any());

    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> productService.save(null));
    }

    @Test
    void shouldUpdate() {

        ProductUpdateRequestDto productUpdateRequestDto = Mockito.mock(ProductUpdateRequestDto.class);
        User user = Mockito.mock(User.class);
        Product product = Mockito.mock(Product.class);

        Mockito.doReturn(product).when(productService).findById(anyLong());

        when(productUpdateRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(productUpdateRequestDto.getProductType()).thenReturn(ProductType.FOOD);
        when(product.getName()).thenReturn("Bread");

        when(taxService.getCalculatedTax(BigDecimal.ONE,ProductType.FOOD)).thenReturn(BigDecimal.ONE);
        when(userService.findById(anyLong())).thenReturn(user);
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.update(productUpdateRequestDto);

        assertEquals(result.getName(),product.getName());

        verify(userService).findById(anyLong());
        verify(taxService).getCalculatedTax(any(),any());
    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist() {

        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.update(productUpdateRequestDto));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldUpdateById() {
        User user = Mockito.mock(User.class);
        Product product = Mockito.mock(Product.class);

        Mockito.doReturn(product).when(productService).findById(anyLong());
        when(user.getId()).thenReturn(Long.valueOf(1));
        when(product.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(product.getProductType()).thenReturn(ProductType.FOOD);
        when(product.getUser()).thenReturn(user);
        when(product.getName()).thenReturn("Bread");

        when(taxService.getCalculatedTax(BigDecimal.ONE,ProductType.FOOD)).thenReturn(BigDecimal.ONE);
        when(userService.findById(Long.valueOf(1))).thenReturn(user);
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.updateById(product.getId());

        assertEquals(result.getName(),product.getName());

        verify(userService).findById(anyLong());
        verify(taxService).getCalculatedTax(any(),any());

    }

    @Test
    void shouldNotUpdateByIdWhenCustomerDoesNotExist() {

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.updateById(anyLong()));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {
        Product product = mock(Product.class);

        doReturn(product).when(productService).findById(anyLong());

        productService.delete(anyLong());

        verify(productRepository).delete(any());
        verify(productService).findById(anyLong());
    }
    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.delete(anyLong()));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        Product product = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));

        Product result = productService.findById(anyLong());

        assertEquals(result.getId(), product.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> productService.findById(anyLong()));
        verify(productRepository).findById(anyLong());
    }
}