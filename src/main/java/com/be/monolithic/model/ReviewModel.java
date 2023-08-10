package com.be.monolithic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class ReviewModel extends BaseModel {
    private int rate;
    private String review;
    private UUID buyerUUID;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "ID")
    @ToString.Exclude
    @JsonIgnore
    private ProductModel product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private UserInfo reviewer;
}
