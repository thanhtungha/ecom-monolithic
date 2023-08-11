package com.be.monolithic.service;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;

public interface ICartService extends IBaseService {
    void createCart(UserInfo userInfo);
    Cart addProduct(UserInfo userInfo, Product product);
    Cart removeProduct(UserInfo userInfo, Product product);
    Cart getCart(UserInfo userInfo);
}
