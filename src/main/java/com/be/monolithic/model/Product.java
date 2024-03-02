package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String productName;
    private double price;
    private int inventoryQuantity;
    private double avg_rates;
    @OneToMany(mappedBy = "product")
    private List<Review> reviewList;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user.getId() +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", inventoryQuantity=" + inventoryQuantity +
                ", avg_rates=" + avg_rates +
                '}';
    }
}
