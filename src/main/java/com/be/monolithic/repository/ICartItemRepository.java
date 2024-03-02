package com.be.monolithic.repository;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.CartItem;
import com.be.monolithic.model.CartItemKey;
import com.be.monolithic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItem, CartItemKey> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
