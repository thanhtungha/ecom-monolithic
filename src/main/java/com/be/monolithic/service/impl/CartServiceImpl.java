package com.be.monolithic.service.impl;

import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.CartItem;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.ICartItemRepository;
import com.be.monolithic.repository.ICartRepository;
import com.be.monolithic.service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements ICartService {
    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;

    @Override
    public void createCart(User userInfo) {
        Optional<Cart> optional = cartRepository.findByUser(userInfo);
        if (optional.isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(userInfo);
            cart.setCreatedAt(new Date());
            cart.setUpdatedAt(new Date());
            cartRepository.save(cart);
        }
    }

    @Override
    public Cart addProduct(User user, Product product) {
        Optional<Cart> optional = cartRepository.findByUser(user);
        if (optional.isPresent()) {
            Cart cart = optional.get();
            CartItem cartItem;
            Optional<CartItem> optionalCartItem =
                    cartItemRepository.findByCartAndProduct(cart, product);
            if (optionalCartItem.isPresent()) {
                cart.getItemList().removeIf(item -> item.getProduct()
                        .getId()
                        .equals(product.getId()));

                cartItem = optionalCartItem.get();
                cartItem.increaseQuantity();
            } else {
                cartItem = new CartItem(cart, product, 1);
            }
            cart.getItemList().add(cartItem);
            cart.setUpdatedAt(new Date());
            cartRepository.save(cart);
            return cart;
        } else {
            throw new RestExceptions.InternalServerError(
                    "Cannot find user's cart.");
        }
    }

    @Override
    public Cart removeProduct(User user, Product product) {
        Optional<Cart> optional = cartRepository.findByUser(user);
        if (optional.isPresent()) {
            Cart cart = optional.get();
            Optional<CartItem> optionalCartItem =
                    cartItemRepository.findByCartAndProduct(cart, product);
            optionalCartItem.ifPresent(cartItem -> {
                cart.getItemList().removeIf(item -> item.getProduct()
                        .getId()
                        .equals(cartItem.getProduct().getId()));
                cartItemRepository.delete(cartItem);
            });
            cart.setUpdatedAt(new Date());
            cartRepository.save(cart);
            return cart;
        } else {
            throw new RestExceptions.InternalServerError(
                    "Cannot find user's cart.");
        }
    }

    @Override
    public Cart getCart(User user) {
        Optional<Cart> optional = cartRepository.findByUser(user);
        if (optional.isEmpty()) {
            throw new RestExceptions.InternalServerError(
                    "Can not find user's cart!");
        }
        return optional.get();
    }

    @Override
    public boolean deleteUserData(User owner) {
        Optional<Cart> storedModel = cartRepository.findByUser(owner);
        storedModel.ifPresent(cartRepository::delete);
        return true;
    }
}
