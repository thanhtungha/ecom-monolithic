package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.model.Cart;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceImplTest extends AbstractContainerBaseTest {
    @BeforeEach
    void setUp() {
        if (userInfo == null) {
            registerTestUser();
        }
        if (testProduct1 == null) {
            registerTestProduct();
        }
    }

    @Test
    @Order(0)
    void addProduct() {
        Cart result = cartService.addProduct(userInfo, testProduct1);
        List<UUID> productList = result.getItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getProduct().getId())
                .toList();
        assertTrue(productList.contains(testProduct1.getId()));

        Optional<Cart> optional = cartRepository.findByUser(userInfo);
        optional.ifPresentOrElse(cart -> {
            List<UUID> productIds = cart.getItemList()
                    .parallelStream()
                    .map(cartItem -> cartItem.getProduct().getId())
                    .toList();
            assertTrue(productIds.contains(testProduct1.getId()));

        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void removeProduct() {
        Cart result = cartService.removeProduct(userInfo, testProduct1);
        assertTrue(result.getItemList().isEmpty());

        Optional<Cart> optional = cartRepository.findByUser(userInfo);
        optional.ifPresentOrElse(
                cart -> assertTrue(cart.getItemList().isEmpty()),
                () -> fail("test case failed!"));
    }

    @Test
    @Order(1)
    void getCart() {
        Cart result = cartService.getCart(userInfo);
        List<UUID> productList = result.getItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getProduct().getId())
                .toList();
        assertTrue(productList.contains(testProduct1.getId()));

        Optional<Cart> optional = cartRepository.findByUser(userInfo);
        optional.ifPresentOrElse(cart -> {
            List<UUID> itemList = cart.getItemList()
                    .parallelStream()
                    .map(cartItem -> cartItem.getProduct().getId())
                    .toList();
            assertTrue(itemList.contains(testProduct1.getId()));
        }, () -> fail("test case failed!"));
    }
}