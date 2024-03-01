package com.be.monolithic.service.impl;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.IProductRepository;
import com.be.monolithic.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product register(User seller,
                            PdRqRegisterArgs registerArgs) {
        Optional<Product> storedModel =
                productRepository.findByProductName(registerArgs.getName());
        if (storedModel.isPresent()) {
            throw new RestExceptions.Conflict("Product existed");
        }

        Product product =
                productMapper.RegisterArgsToProduct(registerArgs);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setUser(seller);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product update(PdRqUpdateArgs updateArgs) {
        throw new RestExceptions.NotImplemented();
        //Optional<Product> storedModel =
        //        productRepository.findById(UUID.fromString(updateArgs.getId()));
        //if (storedModel.isEmpty()) {
        //    throw new RestExceptions.NotFound("Product does not existed!");
        //}
        //
        //Product product = storedModel.get();
        //product.setUpdatedAt(new Date());
        //product.setName(updateArgs.getName());
        //product.setPrice(updateArgs.getPrice());
        //product.setQuantity(updateArgs.getQuantity());
        //productRepository.save(product);
        //return product;
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
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Product addReview(User buyer,
                             PdRqAddReviewArgs addReviewArgs) {
        throw new RestExceptions.NotImplemented();
        //Optional<Product> storedModel =
        //        productRepository.findById(UUID.fromString(addReviewArgs.getId()));
        //if (storedModel.isEmpty()) {
        //    throw new RestExceptions.NotFound("Product does not existed!");
        //}
        //
        //int rating = addReviewArgs.getRating();
        //String review = addReviewArgs.getReview();
        //
        //Product product = storedModel.get();
        //product.setUpdateDate(new Date());
        //Review reviewModel = new Review();
        //reviewModel.setRate(rating);
        //reviewModel.setReview(review);
        //reviewModel.setBuyerUUID(buyer.getId());
        //reviewModel.setReviewer(buyer);
        //product.getReviews().add(reviewModel);
        //
        //productRepository.save(product);
        //return product;
    }

    @Override
    public boolean deleteUserData(User seller) {
        Optional<List<Product>> storedModel =
                productRepository.findByUser(seller);
        storedModel.ifPresent(productRepository::deleteAll);
        return true;
    }
}
