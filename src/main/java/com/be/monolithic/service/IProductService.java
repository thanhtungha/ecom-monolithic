package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;

public interface IProductService extends IBaseService {
    ProductModel register(UserInfo seller, PdRqRegisterArgs registerArgs);
    ProductModel update(PdRqUpdateArgs updateArgs);
    boolean remove(String productId);
    ProductModel getProduct(String productId);
    ProductModel addReview(UserInfo buyer, PdRqAddReviewArgs addReviewArgs);
}
