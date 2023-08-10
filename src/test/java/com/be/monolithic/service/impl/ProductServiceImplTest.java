package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.ReviewModel;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.repository.ProductRepository;
import com.be.monolithic.service.IAuthService;
import com.be.monolithic.service.IProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IProductService productService;

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private IAuthService authService;
    private static UserInfo user;

    @BeforeEach
    void setUp() {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                "userPassword", "0123456789");
        try {
            authService.register(registerArgs, new Inventory());
        } catch (Exception e) {
            //do nothing
        }
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        user = authService.login(loginArgs);
    }

    @Test
    @Order(0)
    void register() {
        PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("productName",
                300);
        ProductModel responseProduct = productService.register(user,
                registerArgs);
        Optional<ProductModel> createdProduct = productRepository.findByName(
                "productName");

        if (createdProduct.isPresent()) {
            ProductModel dbProduct = createdProduct.get();
            assertEquals(registerArgs.getName(), dbProduct.getName());
            assertEquals(registerArgs.getPrice(), dbProduct.getPrice());
            assertEquals(user.getId(), dbProduct.getSellerUUID());

            assertEquals(registerArgs.getName(), responseProduct.getName());
            assertEquals(registerArgs.getPrice(), responseProduct.getPrice());
            assertEquals(user.getId(), responseProduct.getSellerUUID());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void update() {
        PdRqUpdateArgs updateArgs = new PdRqUpdateArgs(getProductId(),
                "updatedName", 300, 10);
        ProductModel responseProduct = productService.update(updateArgs);
        Optional<ProductModel> createdProduct = productRepository.findByName(
                "updatedName");

        if (createdProduct.isPresent()) {
            ProductModel dbProduct = createdProduct.get();
            assertEquals(updateArgs.getName(), dbProduct.getName());
            assertEquals(updateArgs.getQuantity(), dbProduct.getQuantity());
            assertEquals(updateArgs.getPrice(), dbProduct.getPrice());

            assertEquals(updateArgs.getName(), responseProduct.getName());
            assertEquals(updateArgs.getQuantity(),
                    responseProduct.getQuantity());
            assertEquals(updateArgs.getPrice(), responseProduct.getPrice());

            updateArgs.setName("productName");
            updateArgs.setPrice(0);
            updateArgs.setQuantity(0);
            productService.update(updateArgs);
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void remove() {
        boolean result = productService.remove(getProductId());
        Optional<ProductModel> removedProduct = productRepository.findByName(
                "productName");

        if (removedProduct.isPresent() || !result) {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void getProduct() {
        ProductModel responseProduct =
                productService.getProduct(getProductId());
        Optional<ProductModel> createdProduct = productRepository.findByName(
                "productName");

        if (createdProduct.isPresent()) {
            ProductModel dbProduct = createdProduct.get();
            assertEquals(dbProduct.getName(), responseProduct.getName());
            assertEquals(dbProduct.getQuantity(),
                    responseProduct.getQuantity());
            assertEquals(dbProduct.getPrice(), responseProduct.getPrice());
            assertEquals(dbProduct.getRating(), responseProduct.getRating());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void addReview() {
        PdRqAddReviewArgs rateArgs = new PdRqAddReviewArgs(getProductId(), 5,
                "review text");

        ProductModel responseProduct = productService.addReview(user, rateArgs);
        List<ReviewModel> reviews = responseProduct.getReviews();
        //response test
        if (!reviews.isEmpty()) {
            ReviewModel reviewModel = reviews.get(0);
            assertEquals(rateArgs.getRating(), reviewModel.getRate());
            assertEquals(rateArgs.getReview(), reviewModel.getReview());
            assertEquals(user.getId(), reviewModel.getBuyerUUID());
        } else {
            fail("test case failed!");
        }

        //DB test
        Optional<ProductModel> createdProduct = productRepository.findByName(
                "productName");
        if (createdProduct.isPresent()) {
            ProductModel dbProduct = createdProduct.get();
            reviews = new ArrayList<>(dbProduct.getReviews());
            if (!reviews.isEmpty()) {
                ReviewModel reviewModel = reviews.get(0);
                assertEquals(rateArgs.getRating(), reviewModel.getRate());
                assertEquals(rateArgs.getReview(), reviewModel.getReview());
                assertEquals(user.getId(), reviewModel.getBuyerUUID());
                return;
            }
        }
        fail("test case failed!");
    }

    String getProductId() {
        Optional<ProductModel> createdProduct = productRepository.findByName(
                "productName");
        if (createdProduct.isEmpty()) {
            fail("test case failed!");
        }
        return createdProduct.get().getId().toString();
    }
}