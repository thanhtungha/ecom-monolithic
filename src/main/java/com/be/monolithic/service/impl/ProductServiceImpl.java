package com.be.monolithic.service.impl;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.ProductMapper;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.ReviewModel;
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
    public ProductModel register(UserInfo seller,
                                 PdRqRegisterArgs registerArgs) {
        Optional<ProductModel> storedModel =
                productRepository.findByName(registerArgs.getName());
        if (storedModel.isPresent()) {
            throw new RestExceptions.Conflict("Product existed");
        }

        ProductModel productModel =
                productMapper.RegisterArgsToProductModel(registerArgs);
        productModel.setCreateDate(new Date());
        productModel.setUpdateDate(new Date());
        productModel.setSellerUUID(seller.getId());
        productRepository.save(productModel);
        return productModel;
    }

    @Override
    public ProductModel update(PdRqUpdateArgs updateArgs) {
        Optional<ProductModel> storedModel =
                productRepository.findById(UUID.fromString(updateArgs.getId()));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        ProductModel productModel = storedModel.get();
        productModel.setUpdateDate(new Date());
        productModel.setName(updateArgs.getName());
        productModel.setPrice(updateArgs.getPrice());
        productModel.setQuantity(updateArgs.getQuantity());
        productRepository.save(productModel);
        return productModel;
    }

    @Override
    public boolean remove(String productId) {
        Optional<ProductModel> storedModel =
                productRepository.findById(UUID.fromString(productId));
        if (storedModel.isPresent()) {
            productRepository.delete(storedModel.get());
            return true;
        } else {
            throw new RestExceptions.NotFound("Product does not existed!");
        }
    }

    @Override
    public ProductModel getProduct(String productId) {
        Optional<ProductModel> storedModel =
                productRepository.findById(UUID.fromString(productId));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        return storedModel.get();
    }

    @Override
    public ProductModel addReview(UserInfo buyer,
                                  PdRqAddReviewArgs addReviewArgs) {
        Optional<ProductModel> storedModel =
                productRepository.findById(UUID.fromString(addReviewArgs.getProductId()));
        if (storedModel.isEmpty()) {
            throw new RestExceptions.NotFound("Product does not existed!");
        }

        int rating = addReviewArgs.getRating();
        String review = addReviewArgs.getReview();

        ProductModel productModel = storedModel.get();
        productModel.setUpdateDate(new Date());
        ReviewModel reviewModel = new ReviewModel(rating, review,
                buyer.getId(), productModel, buyer);
        productModel.getReviews().add(reviewModel);

        productRepository.save(productModel);
        return productModel;
    }

    @Override
    public boolean deleteUserData(UserInfo seller) {
        Optional<List<ProductModel>> storedModel =
                productRepository.findBySeller(seller);
        storedModel.ifPresent(productRepository::deleteAll);
        return true;
    }
}
