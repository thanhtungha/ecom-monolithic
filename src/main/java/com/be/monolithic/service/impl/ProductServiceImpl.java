package com.be.monolithic.service.impl;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.Review;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.IProductRepository;
import com.be.monolithic.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final IProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Product register(User seller, PdRqRegisterArgs args) {
        Optional<Product> optional = repository.findByProductName(
                args.getName());
        if (optional.isPresent()) {
            throw new RestExceptions.Conflict("Product name is existed!");
        }

        Product product = mapper.RegisterArgsToProduct(args);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setUser(seller);
        repository.save(product);
        return product;
    }

    @Override
    public Product update(PdRqUpdateArgs args) {
        Optional<Product> optional = repository.findById(
                UUID.fromString(args.getId()));
        if (optional.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        Product product = optional.get();
        product.setUpdatedAt(new Date());
        product.setProductName(args.getName());
        product.setPrice(args.getPrice());
        product.setInventoryQuantity(args.getQuantity());
        repository.save(product);
        return product;
    }

    @Override
    public boolean remove(String productId) {
        Optional<Product> optional = repository.findById(
                UUID.fromString(productId));
        optional.ifPresentOrElse(repository::delete, () -> {
            throw new RestExceptions.NotFound("Product does not existed!");
        });
        return true;
    }

    @Override
    public Product getProduct(String productId) {
        Optional<Product> optional = repository.findById(
                UUID.fromString(productId));
        if (optional.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        return optional.get();
    }

    @Override
    public List<Product> getProductList() {
        return repository.findAll();
    }

    @Override
    public Product addReview(User buyer, PdRqAddReviewArgs args) {
        Optional<Product> optional =
                repository.findByIdWithReviewList(UUID.fromString(args.getId()));
        if (optional.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        Product product = optional.get();

        product.setUpdatedAt(new Date());

        Review review = new Review();
        review.setBuyer(buyer);
        review.setProduct(product);
        review.setRating(args.getRating());
        review.setReviewComment(args.getReview());
        review.setCreatedAt(new Date());
        review.setUpdatedAt(new Date());

        Hibernate.initialize(product.getReviewList());
        product.getReviewList().add(review);

        repository.save(product);
        return product;
    }
}
