package com.be.monolithic.controller;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest extends AbstractContainerBaseTest {
    private final String BASE_API = "/api/order";
    private static final List<ProductArgs> productList = new ArrayList<>();
    private static String orderId;

    @BeforeEach
    void setUp() {
        if (seller == null) {
            registerTestUser();
        }
        if (testProduct1 == null) {
            registerTestProduct();
        }
        if (productList.isEmpty()) {
            ProductArgs productArgs1 =
                    new ProductArgs(testProduct1.getId().toString(), 10);
            ProductArgs productArgs2 =
                    new ProductArgs(testProduct2.getId().toString(), 15);
            productList.add(productArgs1);
            productList.add(productArgs2);
        }
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void createOrder() throws Exception {
        fail("test case not implemented!");
        //OrRqCreateOrderArgs args = new OrRqCreateOrderArgs(productList);
        //String reqString = objectMapper.writeValueAsString(args);
        //RequestBuilder requestBuilder =
        //        MockMvcRequestBuilders.post(BASE_API + "/create-order").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        //mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        //
        ////check response
        //
        ////check db
        //Optional<List<Order>> orderOptional =
        //        orderRepository.findByOwner(userInfo);
        //if (orderOptional.isPresent()) {
        //    Order order = orderOptional.get().get(0);
        //    if (!order.getOrderItems().isEmpty()) {
        //        assertEquals(productList.size(), order.getOrderItems().size());
        //        assertEquals(userInfo.getId(), order.getOwner().getId());
        //        orderId = order.getId().toString();
        //        return;
        //    }
        //}
        //fail("test case failed!");
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void updateOrder() throws Exception {
        fail("test case not implemented!");
        //productList.remove(0);
        //OrRqUpdateOrderArgs args = new OrRqUpdateOrderArgs(orderId,
        //        productList);
        //String reqString = objectMapper.writeValueAsString(args);
        //RequestBuilder requestBuilder =
        //        MockMvcRequestBuilders.post(BASE_API + "/update-order").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        //mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        //
        ////check response
        //
        ////check db
        //Optional<List<Order>> orderOptional =
        //        orderRepository.findByOwner(userInfo);
        //if (orderOptional.isPresent()) {
        //    Order order = orderOptional.get().get(0);
        //    if (!order.getOrderItems().isEmpty()) {
        //        assertEquals(productList.size(), order.getOrderItems().size());
        //        return;
        //    }
        //}
        //fail("test case failed!");
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void cancelOrder() throws Exception {
        fail("test case not implemented!");
        //OrRqCancelOrderArgs args = new OrRqCancelOrderArgs(orderId);
        //String reqString = objectMapper.writeValueAsString(args);
        //RequestBuilder requestBuilder =
        //        MockMvcRequestBuilders.post(BASE_API + "/cancelBuyingOrder-order").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        //mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        //
        ////check response
        //
        ////check db
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getOrder() throws Exception {
        fail("test case not implemented!");
        //OrRqGetOrderArgs args = new OrRqGetOrderArgs(orderId);
        //String reqString = objectMapper.writeValueAsString(args);
        //RequestBuilder requestBuilder =
        //        MockMvcRequestBuilders.get(BASE_API + "/get-order").contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, authorizationHeader).content(reqString);
        //mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        //
        ////check response
        //
        ////check db
        //Optional<List<Order>> orderOptional =
        //        orderRepository.findByOwner(userInfo);
        //if (orderOptional.isPresent()) {
        //    Order order = orderOptional.get().get(0);
        //    if (!order.getOrderItems().isEmpty()) {
        //        assertEquals(productList.size(), order.getOrderItems().size());
        //        return;
        //    }
        //}
        //fail("test case failed!");
    }
}