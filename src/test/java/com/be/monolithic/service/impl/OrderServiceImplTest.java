package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.OrderItem;
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
    private static Order createdOrder;

    @BeforeEach
    void setUp() {
        if (buyer == null) {
            registerTestUser();
        }
        if (testProduct1 == null) {
            registerTestProduct();
        }
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void create() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(testProduct1);
        orderItem1.setQuantity(3);
        orderItems.add(orderItem1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(testProduct2);
        orderItem2.setQuantity(5);
        orderItems.add(orderItem2);

        Order result = orderService.create(buyer, orderItems);

        Optional<Order> optional = orderRepository.findById(result.getId());
        optional.ifPresentOrElse(order -> {
            assertEquals(order.getId(), result.getId());
            assertEquals(order.getBuyer().getId(), result.getBuyer().getId());
            createdOrder = result;
        }, () -> fail("test case failed!"));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void update() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(testProduct1);
        orderItem1.setQuantity(1);
        orderItems.add(orderItem1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(testProduct2);
        orderItem2.setQuantity(4);
        orderItems.add(orderItem2);

        Order result = orderService.update(buyer,
                createdOrder.getId().toString(), orderItems);
        assertEquals(orderItems.size(), result.getItemList().size());

        Optional<Order> optional =
                orderRepository.findById(
                        UUID.fromString(createdOrder.getId().toString()));
        optional.ifPresentOrElse(order -> {
            List<OrderItem> orderItemList = order.getItemList();
            assertEquals(orderItems.size(), orderItemList.size());
            createdOrder = result;
        }, () -> fail("test case failed!"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void cancel() {
        orderService.cancelBuyingOrder(buyer, createdOrder.getId().toString());
        Optional<Order> optional = orderRepository.findById(createdOrder.getId());
        if (optional.isPresent()) {
            fail("test case failed!");
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getOrder() {
        Order result = orderService.getBuyingOrder(buyer,
                createdOrder.getId().toString());
        List<OrderItem> orderItems = result.getItemList();
        assertEquals(createdOrder.getItemList().size(), orderItems.size());

        Optional<Order> optional = orderRepository.findById(createdOrder.getId());

        optional.ifPresentOrElse(order -> {
            List<OrderItem> orderItemList = order.getItemList();
            assertEquals(createdOrder.getItemList().size(), orderItemList.size());
        }, () -> fail("test case failed!"));
    }
}