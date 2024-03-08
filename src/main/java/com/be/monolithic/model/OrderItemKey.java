package com.be.monolithic.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class OrderItemKey implements Serializable {
    private UUID order;
    private UUID product;

    public OrderItemKey() {
    }

    public OrderItemKey(UUID order, UUID product) {
        this.order = order;
        this.product = product;
    }
}
