package com.be.monolithic.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CartItemKey implements Serializable {
    private UUID cart;
    private UUID product;
    public CartItemKey() {}

    public CartItemKey(UUID cart, UUID product) {
        this.cart = cart;
        this.product = product;
    }
}