package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.CartRepository;
import com.be.monolithic.service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;

    @Override
    public void createCart(User userInfo) {
        Optional<Cart> stored = cartRepository.findByOwner(userInfo);
        if (stored.isEmpty()) {
            Cart cart = new Cart();
            cart.setOwner(userInfo);
            cart.setCreateDate(new Date());
            cart.setUpdateDate(new Date());
            cartRepository.save(cart);
        }
    }

    @Override
    public Cart addProduct(User userInfo, Product product) {
        Optional<Cart> stored = cartRepository.findByOwner(userInfo);
        if (stored.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's cart!");
        }
        Cart dbCart = stored.get();
        dbCart.getProducts().add(product);
        cartRepository.save(dbCart);
        return dbCart;
    }

    @Override
    public Cart removeProduct(User userInfo, Product product) {
        Optional<Cart> stored = cartRepository.findByOwner(userInfo);
        if (stored.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's cart!");
        }
        Cart dbCart = stored.get();
        Product dbProduct = null;
        for (Product prd : dbCart.getProducts()) {
            if (prd.getId().equals(product.getId())) {
                dbProduct = prd;
                break;
            }
        }
        if (dbProduct != null) {
            dbCart.getProducts().remove(dbProduct);
            cartRepository.save(dbCart);
        }
        return dbCart;
    }

    @Override
    public Cart getCart(User userInfo) {
        Optional<Cart> stored = cartRepository.findByOwner(userInfo);
        if (stored.isEmpty()) {
            throw new RestExceptions.InternalServerError("Can not find " +
                    "user's cart!");
        }
        return stored.get();
    }

    @Override
    public boolean deleteUserData(User owner) {
        Optional<Cart> storedModel = cartRepository.findByOwner(owner);
        storedModel.ifPresent(cartRepository::delete);
        return true;
    }
}
