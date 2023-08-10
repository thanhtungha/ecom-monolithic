package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory extends BaseModel {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private UserInfo seller;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, fetch =
            FetchType.EAGER, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
