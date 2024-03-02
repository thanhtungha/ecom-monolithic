package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product")
@IdClass(CartItemKey.class)
public class CartItem {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public void increaseQuantity() {
        quantity++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CartItem cartItem))
            return false;
        return cart.equals(cartItem.cart) && product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, product);
    }
}
