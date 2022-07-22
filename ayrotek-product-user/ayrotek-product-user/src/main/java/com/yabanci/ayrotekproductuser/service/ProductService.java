package com.yabanci.ayrotekproductuser.service;

import com.yabanci.ayrotekproductuser.converter.ProductMapper;
import com.yabanci.ayrotekproductuser.dto.ProductDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotekproductuser.enums.ProductErrorMessage;
import com.yabanci.ayrotekproductuser.exception.ItemNotFoundException;
import com.yabanci.ayrotekproductuser.model.Product;
import com.yabanci.ayrotekproductuser.model.ProductType;
import com.yabanci.ayrotekproductuser.model.User;
import com.yabanci.ayrotekproductuser.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final TaxService taxService;

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return productDtoList;
    }
    public List<ProductDto> findAllByProductType(ProductType productType){
        List<Product> products = productRepository.findAllByProductType(productType);
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return  productDtoList;
    }

    public ProductDto save(ProductSaveRequestDto productSaveRequestDto){
        Product product = ProductMapper.INSTANCE.convertToProduct(productSaveRequestDto);
        BigDecimal taxIncludePrice = taxService.getCalculatedTax(productSaveRequestDto.getTaxFreePrice(),productSaveRequestDto.getProductType());
        User user = userService.findById(productSaveRequestDto.getUserId());
        product.setTaxIncludedPrice(taxIncludePrice);
        product.setUser(user);
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public ProductDto update(ProductUpdateRequestDto productUpdateRequestDto){
        Product product = findById(productUpdateRequestDto.getId());
        BigDecimal taxIncludePrice = taxService.getCalculatedTax(productUpdateRequestDto.getTaxFreePrice(),productUpdateRequestDto.getProductType());
        User user = userService.findById(productUpdateRequestDto.getUserId());
        product.setUser(user);
        product.setTaxIncludedPrice(taxIncludePrice);
        product.setProductType(productUpdateRequestDto.getProductType());
        product.setName(productUpdateRequestDto.getName());
        product.setTaxFreePrice(productUpdateRequestDto.getTaxFreePrice());
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public ProductDto updateById(long id){
        Product product = findById(id);
        BigDecimal taxIncludePrice = taxService.getCalculatedTax(product.getTaxFreePrice(),product.getProductType());
        User user = userService.findById(product.getUser().getId());
        product.setUser(user);
        product.setTaxIncludedPrice(taxIncludePrice);
        product.setProductType(product.getProductType());
        product.setName(product.getName());
        product.setTaxFreePrice(product.getTaxFreePrice());
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public void delete(long id){
        Product product = findById(id);
        productRepository.delete(product);
    }

    protected Product findById(long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ProductErrorMessage.PRODUCT_NOT_FOUND));
        return product;
    }
}