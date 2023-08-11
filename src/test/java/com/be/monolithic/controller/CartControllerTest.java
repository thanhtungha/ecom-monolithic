package com.be.monolithic.controller;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.cart.CtRqProductArgs;
import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.repository.CartRepository;
import com.be.monolithic.repository.InventoryRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest extends AbstractContainerBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CartRepository cartRepository;
    private final String BASE_API = "/api/cart";

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private AuthRepository authRepository;
    private static UserInfo userInfo;
    private static String authorizationHeader;
    private static String productId;

    @BeforeEach
    void setUp() throws Exception {
        if (userInfo == null) {
            AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                    "userPassword", "1234567890");
            String reqString = objectMapper.writeValueAsString(registerArgs);
            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api"
                    + "/auth/register").contentType(MediaType.APPLICATION_JSON).content(reqString);
            mockMvc.perform(requestBuilder).andExpect(status().isCreated());

            AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName",
                    "userPassword");
            reqString = objectMapper.writeValueAsString(loginArgs);
            requestBuilder =
                    MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(reqString);
            mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

            userInfo = getUserInfo();
            authorizationHeader = "Bearer " + userInfo.getAccessToken();

            //create product
            IvRqAddProductArgs args = new IvRqAddProductArgs("productName",
                    100);
            reqString = objectMapper.writeValueAsString(args);
            requestBuilder =
                    MockMvcRequestBuilders.post("/api/inventory/add-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
            mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

            Optional<Inventory> inventoryOptional =
                    inventoryRepository.findByOwner(userInfo);
            if (inventoryOptional.isPresent()) {
                Inventory inventory = inventoryOptional.get();
                if (!inventory.getProducts().isEmpty()) {
                    Product addedProduct = inventory.getProducts().get(0);
                    productId = addedProduct.getId().toString();
                    return;
                }
            }
            fail("failed to create product!");
        }
    }

    @Test
    @Order(0)
    void addProduct() throws Exception {
        CtRqProductArgs args = new CtRqProductArgs(productId);
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
                assertEquals(productId, addedProduct.getId().toString());
                assertEquals(userInfo.getId(), cart.getOwner().getId());
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(2)
    void removeProduct() throws Exception {
        CtRqProductArgs args = new CtRqProductArgs(productId);
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

    UserInfo getUserInfo() {
        Optional<UserInfo> createdUser = authRepository.findByUserName(
                "userName");
        if (createdUser.isEmpty()) {
            fail("test case failed!");
        }
        return createdUser.get();
    }
}