package com.be.monolithic.service;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;

public interface ICartService extends IBaseService {
    void createCart(User userInfo);
    Cart addProduct(User userInfo, Product product);
    Cart removeProduct(User userInfo, Product product);
    Cart getCart(User userInfo);
}
