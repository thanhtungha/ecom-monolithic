package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.model_old.Product;
import com.be.monolithic.model_old.User;

import java.util.List;

public interface IProductService extends IBaseService {
    Product register(User seller, PdRqRegisterArgs registerArgs);
    Product update(PdRqUpdateArgs updateArgs);
    boolean remove(String productId);
    Product getProduct(String productId);
    List<Product> getProductList();
    Product addReview(User buyer, PdRqAddReviewArgs addReviewArgs);
}
