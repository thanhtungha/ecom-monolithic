package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
@IdClass(OrderItemKey.class)
public class OrderItem {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
    @Id
    @ManyToOne
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
        if (!(o instanceof OrderItem orderItem))
            return false;
        return quantity == orderItem.quantity && order.equals(
                orderItem.order) && product.equals(orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product, quantity);
    }
}
