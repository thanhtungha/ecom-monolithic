package com.be.monolithic.controller;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqRemoveProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateProductArgs;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryControllerTest extends AbstractContainerBaseTest {
    private final String BASE_API = "/api/inventory";
    private static String productId;

    @BeforeEach
    void setUp() {
        if (userInfo == null) {
            registerTestUser();
        }
    }

    @Test
    @Order(0)
    void addProduct() throws Exception {
        IvRqAddProductArgs args = new IvRqAddProductArgs("inventoryProduct", 100);
        String reqString = objectMapper.writeValueAsString(args);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/add-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

        //check response

        //check db
        Optional<Inventory> inventoryOptional =
                inventoryRepository.findByOwner(userInfo);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (!inventory.getProducts().isEmpty()) {
                Product addedProduct = inventory.getProducts().get(0);
                assertEquals(args.getName(), addedProduct.getName());
                assertEquals(args.getPrice(), addedProduct.getPrice());
                assertEquals(userInfo.getId(), inventory.getOwner().getId());
                productId = addedProduct.getId().toString();
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(2)
    void remove() throws Exception {
        IvRqRemoveProductArgs args = new IvRqRemoveProductArgs(productId);
        String reqString = objectMapper.writeValueAsString(args);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/remove-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
        Optional<Inventory> inventoryOptional =
                inventoryRepository.findByOwner(userInfo);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (inventory.getProducts().isEmpty()) {
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(1)
    void updateProduct() throws Exception {
        IvRqUpdateProductArgs args = new IvRqUpdateProductArgs(productId, 
                "new Name", 300, 15);
        String reqString = objectMapper.writeValueAsString(args);
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(BASE_API + "/update-product").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response

        //check db
        Optional<Inventory> inventoryOptional =
                inventoryRepository.findByOwner(userInfo);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if (!inventory.getProducts().isEmpty()) {
                Product updatedProduct = inventory.getProducts().get(0);
                assertEquals(args.getName(), updatedProduct.getName());
                assertEquals(args.getPrice(), updatedProduct.getPrice());
                assertEquals(args.getQuantity(), updatedProduct.getQuantity());
                return;
            }
        }
        fail("test case failed!");
    }

    @Test
    @Order(1)
    void getInventory() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(BASE_API + "/get-inventory").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }
}