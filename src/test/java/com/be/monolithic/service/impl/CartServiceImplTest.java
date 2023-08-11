package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.CartRepository;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.ICartService;
import com.be.monolithic.service.IProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceImplTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;
    private static Product product;
    @Autowired
    private IAuthService authService;
    private static UserInfo user;

    @BeforeEach
    void setUp() {
        if (user == null) {
            AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                    "userPassword", "0123456789");
            authService.register(registerArgs);
            AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName",
                    "userPassword");
            user = authService.login(loginArgs);
            cartService.createCart(user);
        }
        if (product == null) {
            PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("productName"
                    , 300);
            product = productService.register(user, registerArgs);
        }
    }

    @Test
    @Order(0)
    void addProduct() {
        Cart cart = cartService.addProduct(user, product);
        Optional<Cart> createdCart = cartRepository.findByOwner(user);
        if (createdCart.isPresent()) {
            Cart dbCart = createdCart.get();
            List<Product> productList = dbCart.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());

            productList = cart.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void removeProduct() {
        Cart cart = cartService.removeProduct(user, product);
        Optional<Cart> createdCart = cartRepository.findByOwner(user);
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
        Cart cart = cartService.getCart(user);
        Optional<Cart> createdCart = cartRepository.findByOwner(user);
        if (createdCart.isPresent()) {
            Cart dbCart = createdCart.get();
            List<Product> productList = dbCart.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());

            productList = cart.getProducts();
            assertEquals(product.getId(), productList.get(0).getId());
            assertEquals(product.getName(), productList.get(0).getName());
        } else {
            fail("test case failed!");
        }
    }
}