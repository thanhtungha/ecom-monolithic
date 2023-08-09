package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.model.ProductModel;

public interface IProductService {
    ProductModel register(String authorizationHeader, PdRqRegisterArgs registerArgs);
    ProductModel update(PdRqUpdateArgs updateArgs);
    boolean remove(String productId);
    ProductModel getProduct(String productId);
    ProductModel addReview(PdRqAddReviewArgs addReviewArgs);
}
