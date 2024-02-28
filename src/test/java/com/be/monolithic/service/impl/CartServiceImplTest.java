package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.model_old.Cart;
import com.be.monolithic.model_old.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
        Cart cart = cartService.addProduct(userInfo, testProduct1);
        Optional<Cart> createdCart = cartRepository.findByOwner(userInfo);
        if (createdCart.isPresent()) {
            Cart dbCart = createdCart.get();
            List<Product> productList = dbCart.getProducts();
            assertEquals(testProduct1.getId(), productList.get(0).getId());
            assertEquals(testProduct1.getName(), productList.get(0).getName());

            productList = cart.getProducts();
            assertEquals(testProduct1.getId(), productList.get(0).getId());
            assertEquals(testProduct1.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void removeProduct() {
        Cart cart = cartService.removeProduct(userInfo, testProduct1);
        Optional<Cart> createdCart = cartRepository.findByOwner(userInfo);
        if (createdCart.isPresent()) {
            Cart dbCart = createdCart.get();
            assertTrue(dbCart.getProducts().isEmpty());
            assertTrue(cart.getProducts().isEmpty());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void getCart() {
        Cart cart = cartService.getCart(userInfo);
        Optional<Cart> createdCart = cartRepository.findByOwner(userInfo);
        if (createdCart.isPresent()) {
            Cart dbCart = createdCart.get();
            List<Product> productList = dbCart.getProducts();
            assertEquals(testProduct1.getId(), productList.get(0).getId());
            assertEquals(testProduct1.getName(), productList.get(0).getName());

            productList = cart.getProducts();
            assertEquals(testProduct1.getId(), productList.get(0).getId());
            assertEquals(testProduct1.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }
}