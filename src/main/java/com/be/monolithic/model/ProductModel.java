package com.be.monolithic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductModel extends BaseModel {
    private String name;
    private int price;
    private int quantity;
    private double rating;
    private UUID sellerUUID;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER, orphanRemoval = true)
    private List<ReviewModel> reviews;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    @ToString.Exclude
    @JsonIgnore
    private Inventory inventory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserInfo seller;
}
