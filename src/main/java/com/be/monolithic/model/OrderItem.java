package com.be.monolithic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
public class OrderItem {
    private UUID orderId;
    private UUID productId;
    private int quantity;
}
