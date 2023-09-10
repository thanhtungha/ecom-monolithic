package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;

public interface IProductService extends IBaseService {
    Product register(User seller, PdRqRegisterArgs registerArgs);
    Product update(PdRqUpdateArgs updateArgs);
    boolean remove(String productId);
    Product getProduct(String productId);
    Product addReview(User buyer, PdRqAddReviewArgs addReviewArgs);
}
