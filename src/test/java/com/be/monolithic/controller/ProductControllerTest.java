package com.be.monolithic.controller;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest extends AbstractContainerBaseTest {
    private final String BASE_API = "/api/product";

    @BeforeEach
    void setUp() {
        if(userInfo == null) {
            registerTestUser();
        }
    }


    @Test
    @Order(0)
    void register() throws Exception {
        PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("productName",
                100);
        String reqString = objectMapper.writeValueAsString(registerArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/register").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

        //check response

        //check db
        Optional<Product> createdProduct =
                productRepository.findByName(registerArgs.getName());
        if (createdProduct.isPresent()) {
            Product product = createdProduct.get();
            assertEquals(registerArgs.getName(), product.getName());
            assertEquals(registerArgs.getPrice(), product.getPrice());
            assertEquals(userInfo.getId(), product.getSellerUUID());
            productId = product.getId().toString();
            return;
        }
        fail("test case failed!");
    }

    @Test
    @Order(1)
    void update() throws Exception {
        PdRqUpdateArgs updateArgs = new PdRqUpdateArgs(productId, "newName",
                200, 150);
        String reqString = objectMapper.writeValueAsString(updateArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/update").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
        Optional<Product> createdProduct =
                productRepository.findById(UUID.fromString(productId));
        if (createdProduct.isPresent()) {
            Product product = createdProduct.get();
            assertEquals(updateArgs.getName(), product.getName());
            assertEquals(updateArgs.getPrice(), product.getPrice());
            assertEquals(updateArgs.getQuantity(), product.getQuantity());
            return;
        }
        fail("test case failed!");
    }

    @Test
    @Order(3)
    void remove() throws Exception {
        PdRqProductArgs productArgs = new PdRqProductArgs(productId);
        String reqString = objectMapper.writeValueAsString(productArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/remove-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(1)
    void getProduct() throws Exception {
        PdRqProductArgs productArgs = new PdRqProductArgs(productId);
        String reqString = objectMapper.writeValueAsString(productArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(BASE_API + "/get-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(2)
    void addReview() throws Exception {
        PdRqAddReviewArgs reviewArgs = new PdRqAddReviewArgs(productId, 5, "review text");
        String reqString = objectMapper.writeValueAsString(reviewArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/add-review").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
    }
}