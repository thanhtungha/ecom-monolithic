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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImplTest extends AbstractContainerBaseTest {
    @BeforeEach
    void setUp() {
        if (seller == null) {
            registerTestUser();
        }
    }

    @Test
    @Order(0)
    void register() {
        PdRqRegisterArgs registerArgs = new PdRqRegisterArgs("testProduct",
                300);
        Product responseProduct = productService.register(seller,
                registerArgs);
        Optional<Product> optional = productRepository.findByProductName(
                "testProduct");
        optional.ifPresentOrElse(product -> {
            assertEquals(registerArgs.getName(), product.getProductName());
            assertEquals(registerArgs.getPrice(), product.getPrice());
            assertEquals(seller.getId(), product.getUser().getId());

            assertEquals(registerArgs.getName(),
                    responseProduct.getProductName());
            assertEquals(registerArgs.getPrice(), responseProduct.getPrice());
            assertEquals(seller.getId(), responseProduct.getUser().getId());
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(1)
    void update() {
        PdRqUpdateArgs args = new PdRqUpdateArgs(getProductId(),
                "updatedName", 300, 10);
        Product responseProduct = productService.update(args);
        Optional<Product> optional = productRepository.findByProductName(
                "updatedName");
        optional.ifPresentOrElse(product -> {
            assertEquals(args.getName(), product.getProductName());
            assertEquals(args.getQuantity(), product.getInventoryQuantity());
            assertEquals(args.getPrice(), product.getPrice());

            assertEquals(args.getName(), responseProduct.getProductName());
            assertEquals(args.getQuantity(),
                    responseProduct.getInventoryQuantity());
            assertEquals(args.getPrice(), responseProduct.getPrice());

            //change product data to original data for other test cases
            args.setName("testProduct");
            args.setPrice(0);
            args.setQuantity(0);
            productService.update(args);
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void remove() {
        boolean result = productService.remove(getProductId());
        assertTrue(result);

        Optional<Product> optional = productRepository.findByProductName(
                "testProduct");
        optional.ifPresent(product -> fail("test case failed!"));
    }

    @Test
    @Order(1)
    void getProduct() {
        Product responseProduct =
                productService.getProduct(getProductId());
        Optional<Product> optional = productRepository.findByProductName(
                "testProduct");
        optional.ifPresentOrElse(product -> {
            assertEquals(product.getProductName(),
                    responseProduct.getProductName());
            assertEquals(product.getInventoryQuantity(),
                    responseProduct.getInventoryQuantity());
            assertEquals(product.getPrice(), responseProduct.getPrice());
            assertEquals(product.getAvg_rates(),
                    responseProduct.getAvg_rates());
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(1)
    void addReview() {
        PdRqAddReviewArgs args = new PdRqAddReviewArgs(getProductId(), 5,
                "review text");

        Product responseProduct = productService.addReview(seller, args);
        List<Review> reviews = responseProduct.getReviewList();
        //response test
        if (!reviews.isEmpty()) {
            Review review = reviews.get(0);
            assertEquals(args.getRating(), review.getRating());
            assertEquals(args.getReview(), review.getReviewComment());
            assertEquals(seller.getId(), review.getBuyer().getId());
        } else {
            fail("test case failed!");
        }

        //DB test
        Optional<Product> optional = productRepository.findByIdWithReviewList(
                UUID.fromString(getProductId()));
        optional.ifPresentOrElse(product -> {
            List<Review> reviewList = new ArrayList<>(product.getReviewList());
            if (!reviewList.isEmpty()) {
                Review review = reviewList.get(0);
                assertEquals(args.getRating(), review.getRating());
                assertEquals(args.getReview(), review.getReviewComment());
                assertEquals(seller.getId(), review.getBuyer().getId());
            }
        }, ()->fail("test case failed!"));
    }

    String getProductId() {
        Optional<Product> createdProduct = productRepository.findByProductName(
                "testProduct");
        if (createdProduct.isEmpty()) {
            fail("test case failed!");
        }
        return createdProduct.get().getId().toString();
    }
}