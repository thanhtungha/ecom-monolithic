package com.be.monolithic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<ProductModel> products = new ArrayList<>();
}
