package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne()
    @JoinColumn(name = "buyer_id")
    private User buyer;
    @ManyToOne()
    @JoinColumn(name = "seller_id")
    private User seller;
}
