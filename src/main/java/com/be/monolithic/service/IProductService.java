package com.be.monolithic.service;

import com.be.monolithic.dto.product.*;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;

public interface IProductService extends IBaseService {
    Product register(UserInfo seller, PdRqRegisterArgs registerArgs);
    Product update(PdRqUpdateArgs updateArgs);
    boolean remove(String productId);
    Product getProduct(String productId);
    Product addReview(UserInfo buyer, PdRqAddReviewArgs addReviewArgs);
}
