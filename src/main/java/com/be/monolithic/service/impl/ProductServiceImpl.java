package com.be.monolithic.service.impl;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.Review;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.ProductRepository;
import com.be.monolithic.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product register(UserInfo seller,
                            PdRqRegisterArgs registerArgs) {
        Optional<Product> storedModel =
                productRepository.findByName(registerArgs.getName());
        if (storedModel.isPresent()) {
            throw new RestExceptions.Conflict("Product existed");
        }

        Product product =
                productMapper.RegisterArgsToProduct(registerArgs);
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        product.setSellerUUID(seller.getId());
        productRepository.save(product);
        return product;
    }

    @Override
    public Product update(PdRqUpdateArgs updateArgs) {
        Optional<Product> storedModel =
                productRepository.findById(UUID.fromString(updateArgs.getId()));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        Product product = storedModel.get();
        product.setUpdateDate(new Date());
        product.setName(updateArgs.getName());
        product.setPrice(updateArgs.getPrice());
        product.setQuantity(updateArgs.getQuantity());
        productRepository.save(product);
        return product;
    }

    @Override
    public boolean remove(String productId) {
        Optional<Product> storedModel =
                productRepository.findById(UUID.fromString(productId));
        if (storedModel.isPresent()) {
            productRepository.delete(storedModel.get());
            return true;
        } else {
            throw new RestExceptions.NotFound("Product does not existed!");
        }
    }

    @Override
    public Product getProduct(String productId) {
        Optional<Product> storedModel =
                productRepository.findById(UUID.fromString(productId));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        return storedModel.get();
    }

    @Override
    public Product addReview(UserInfo buyer,
                             PdRqAddReviewArgs addReviewArgs) {
        Optional<Product> storedModel =
                productRepository.findById(UUID.fromString(addReviewArgs.getProductId()));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        int rating = addReviewArgs.getRating();
        String review = addReviewArgs.getReview();

        Product product = storedModel.get();
        product.setUpdateDate(new Date());
        Review reviewModel = new Review();
        reviewModel.setRate(rating);
        reviewModel.setReview(review);
        reviewModel.setBuyerUUID(buyer.getId());
        reviewModel.setReviewer(buyer);
        product.getReviews().add(reviewModel);

        productRepository.save(product);
        return product;
    }

    @Override
    public boolean deleteUserData(UserInfo seller) {
        Optional<List<Product>> storedModel =
                productRepository.findBySeller(seller);
        storedModel.ifPresent(productRepository::deleteAll);
        return true;
    }
}
