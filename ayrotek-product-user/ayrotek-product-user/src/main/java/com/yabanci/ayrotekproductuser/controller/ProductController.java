package com.yabanci.ayrotekproductuser.controller;

import com.yabanci.ayrotekproductuser.dto.GeneralResponseDto;
import com.yabanci.ayrotekproductuser.dto.ProductDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotekproductuser.model.ProductType;
import com.yabanci.ayrotekproductuser.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity findAll(){
        List<ProductDto> productDtoList = productService.findAll();
        return ResponseEntity.ok(GeneralResponseDto.of(productDtoList));
    }

    @GetMapping("/{productType}")
    public ResponseEntity findAllByProductType(@PathVariable ProductType productType){
        List<ProductDto> productDtoList = productService.findAllByProductType(productType);
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        ProductDto productDto = productService.save(productSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        ProductDto productDto = productService.update(productUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable int id){
        ProductDto productDto = productService.updateById(id);
        return ResponseEntity.ok(productDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        productService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("Product Deleted!"));
    }
}