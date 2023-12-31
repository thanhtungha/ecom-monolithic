package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
import com.be.monolithic.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest extends AbstractContainerBaseTest {
    private static final List<OrderItem> orderItems = new ArrayList<>();
    private static String orderId;

    @BeforeEach
    void setUp() {
        if (userInfo == null) {
            registerTestUser();
        }
        if (orderItems.isEmpty()) {
            OrderItem item = new OrderItem(testProduct1, 30);
            orderItems.add(item);
            item = new OrderItem(testProduct2, 45);
            orderItems.add(item);
        }
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void create() {
        Order order = orderService.create(userInfo, orderItems);
        Optional<Order> createdOrder = orderRepository.findById(order.getId());
        if (createdOrder.isPresent()) {
            Order dbOrder = createdOrder.get();
            List<OrderItem> orderItemList = dbOrder.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());

            orderItemList = order.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());
            orderId = order.getId().toString();
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void update() {
        orderItems.remove(0);
        Order order = orderService.update(userInfo, orderId, orderItems);
        Optional<Order> createdOrder =
                orderRepository.findById(UUID.fromString(orderId));
        if (createdOrder.isPresent()) {
            Order dbOrder = createdOrder.get();
            List<OrderItem> orderItemList = dbOrder.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());

            orderItemList = order.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void cancel() {
        boolean result = orderService.cancel(userInfo, orderId);
        Optional<Order> createdOrder =
                orderRepository.findById(UUID.fromString(orderId));
        if (createdOrder.isPresent() || !result) {
            fail("test case failed!");
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getOrder() {
        Order order = orderService.getOrder(userInfo, orderId);
        Optional<Order> createdOrder =
                orderRepository.findById(UUID.fromString(orderId));
        if (createdOrder.isPresent()) {
            Order dbOrder = createdOrder.get();
            List<OrderItem> orderItemList = dbOrder.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());

            orderItemList = order.getOrderItems();
            assertEquals(orderItems.size(), orderItemList.size());
        } else {
            fail("test case failed!");
        }
    }
}