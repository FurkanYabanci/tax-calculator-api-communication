package com.yabanci.ayrotekproductuser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yabanci.ayrotekproductuser.dto.GeneralResponseDto;
import com.yabanci.ayrotekproductuser.dto.ProductDto;
import com.yabanci.ayrotekproductuser.dto.UserDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.ProductUpdateRequestDto;
import com.yabanci.ayrotekproductuser.model.Product;
import com.yabanci.ayrotekproductuser.model.ProductType;
import com.yabanci.ayrotekproductuser.model.User;
import com.yabanci.ayrotekproductuser.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.DataInput;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductControllerTest extends BaseControllerTest {

    private static final String BASE_PATH = "/api/v1/products";

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_PATH)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void findAllByProductType() throws Exception {
       /* MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_PATH+"/FOOD")
                        .content(ProductType.FOOD.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);*/
    }



    @Test
    void save() throws Exception {

        ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto();
        productSaveRequestDto.setUserId(Long.valueOf(1));
        productSaveRequestDto.setName("Bread");
        productSaveRequestDto.setProductType(ProductType.FOOD);
        productSaveRequestDto.setTaxFreePrice(BigDecimal.ONE);

        String content = objectMapper.writeValueAsString(productSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void update() throws Exception {
        ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto();
        productUpdateRequestDto.setUserId(Long.valueOf(1));
        productUpdateRequestDto.setName("Bread");
        productUpdateRequestDto.setProductType(ProductType.FOOD);
        productUpdateRequestDto.setTaxFreePrice(BigDecimal.ONE);
        productUpdateRequestDto.setId(Long.valueOf(1));

        String content = objectMapper.writeValueAsString(productUpdateRequestDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_PATH+"/2202")
                        .content("2202")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}