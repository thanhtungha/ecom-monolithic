package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.product.PdRqAddReviewArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.Review;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest extends AbstractContainerBaseTest {
    @BeforeEach
    void setUp() {
        if(userInfo == null) {
            registerTestUser();
        }
    }

    @Test
    @Order(0)
    void register() {
        PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("testProduct",
                300);
        Product responseProduct = productService.register(userInfo,
                registerArgs);
        Optional<Product> createdProduct = productRepository.findByName(
                "testProduct");

        if (createdProduct.isPresent()) {
            Product dbProduct = createdProduct.get();
            assertEquals(registerArgs.getName(), dbProduct.getName());
            assertEquals(registerArgs.getPrice(), dbProduct.getPrice());
            assertEquals(userInfo.getId(), dbProduct.getSellerUUID());

            assertEquals(registerArgs.getName(), responseProduct.getName());
            assertEquals(registerArgs.getPrice(), responseProduct.getPrice());
            assertEquals(userInfo.getId(), responseProduct.getSellerUUID());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void update() {
        PdRqUpdateArgs updateArgs = new PdRqUpdateArgs(getProductId(),
                "updatedName", 300, 10);
        Product responseProduct = productService.update(updateArgs);
        Optional<Product> createdProduct = productRepository.findByName(
                "updatedName");

        if (createdProduct.isPresent()) {
            Product dbProduct = createdProduct.get();
            assertEquals(updateArgs.getName(), dbProduct.getName());
            assertEquals(updateArgs.getQuantity(), dbProduct.getQuantity());
            assertEquals(updateArgs.getPrice(), dbProduct.getPrice());

            assertEquals(updateArgs.getName(), responseProduct.getName());
            assertEquals(updateArgs.getQuantity(),
                    responseProduct.getQuantity());
            assertEquals(updateArgs.getPrice(), responseProduct.getPrice());

            updateArgs.setName("testProduct");
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
        Optional<Product> removedProduct = productRepository.findByName(
                "testProduct");

        if (removedProduct.isPresent() || !result) {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void getProduct() {
        Product responseProduct =
                productService.getProduct(getProductId());
        Optional<Product> createdProduct = productRepository.findByName(
                "testProduct");

        if (createdProduct.isPresent()) {
            Product dbProduct = createdProduct.get();
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

        Product responseProduct = productService.addReview(userInfo, rateArgs);
        List<Review> reviews = responseProduct.getReviews();
        //response test
        if (!reviews.isEmpty()) {
            Review review = reviews.get(0);
            assertEquals(rateArgs.getRating(), review.getRate());
            assertEquals(rateArgs.getReview(), review.getReview());
            assertEquals(userInfo.getId(), review.getBuyerUUID());
        } else {
            fail("test case failed!");
        }

        //DB test
        Optional<Product> createdProduct = productRepository.findByName(
                "testProduct");
        if (createdProduct.isPresent()) {
            Product dbProduct = createdProduct.get();
            reviews = new ArrayList<>(dbProduct.getReviews());
            if (!reviews.isEmpty()) {
                Review review = reviews.get(0);
                assertEquals(rateArgs.getRating(), review.getRate());
                assertEquals(rateArgs.getReview(), review.getReview());
                assertEquals(userInfo.getId(), review.getBuyerUUID());
                return;
            }
        }
        fail("test case failed!");
    }

    String getProductId() {
        Optional<Product> createdProduct = productRepository.findByName(
                "testProduct");
        if (createdProduct.isEmpty()) {
            fail("test case failed!");
        }
        return createdProduct.get().getId().toString();
    }
}