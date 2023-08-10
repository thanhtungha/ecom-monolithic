package com.be.monolithic.controller;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    private final String BASE_API = "/api/product";
    private static String productId;

    @Autowired
    private AuthRepository authRepository;
    private static UserInfo userInfo;
    private static String authorizationHeader;

    @BeforeEach
    void setUp() throws Exception {
        if(userInfo == null) {
            AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName", "userPassword", "1234567890");
            String reqString = objectMapper.writeValueAsString(registerArgs);
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api" + "/auth/register").contentType(MediaType.APPLICATION_JSON).content(reqString);
            mockMvc.perform(requestBuilder).andExpect(status().isCreated());

            AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
            reqString = objectMapper.writeValueAsString(loginArgs);
            requestBuilder = MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(reqString);
            mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

            userInfo = getUserInfo();
            authorizationHeader = "Bearer " + userInfo.getAccessToken();
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

        //check response

        //check db
    }

    @Test
    @Order(1)
    void getProduct() throws Exception {
        PdRqProductArgs productArgs = new PdRqProductArgs(productId);
        String reqString = objectMapper.writeValueAsString(productArgs);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/get-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response
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

    UserInfo getUserInfo() {
        Optional<UserInfo> createdUser = authRepository.findByUserName(
                "userName");
        if (createdUser.isEmpty()) {
            fail("test case failed!");
        }
        return createdUser.get();
    }
}