package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UUID sellerId;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER, orphanRemoval = true)
    private List<ReviewModel> reviews;
}