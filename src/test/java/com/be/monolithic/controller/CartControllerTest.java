package com.be.monolithic.controller;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.cart.CtRqProductArgs;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest extends AbstractContainerBaseTest {
    private final String BASE_API = "/api/cart";

    @BeforeEach
    void setUp() {
        if (userInfo == null) {
            registerTestUser();
        }
        if(testProduct1 == null) {
            registerTestProduct();
        }
    }

    @Test
    @Order(0)
    void addProduct() throws Exception {
        CtRqProductArgs args = new CtRqProductArgs(testProductInventory.getId().toString());
        String reqString = objectMapper.writeValueAsString(args);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/add-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
        Optional<Cart> cartOptional = cartRepository.findByOwner(userInfo);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            if (!cart.getProducts().isEmpty()) {
                Product addedProduct = cart.getProducts().get(0);
                assertEquals(testProductInventory.getId().toString(), addedProduct.getId().toString());
                assertEquals(userInfo.getId(), cart.getOwner().getId());
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(2)
    void removeProduct() throws Exception {
        CtRqProductArgs args = new CtRqProductArgs(testProductInventory.getId().toString());
        String reqString = objectMapper.writeValueAsString(args);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/remove-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
        Optional<Cart> cartOptional = cartRepository.findByOwner(userInfo);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            if (cart.getProducts().isEmpty()) {
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(1)
    void getCart() throws Exception{
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(BASE_API + "/get-cart").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response
    }
}