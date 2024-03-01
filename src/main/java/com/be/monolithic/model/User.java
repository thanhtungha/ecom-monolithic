package com.be.monolithic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private String userName;
    private String userPassword;
    private String phoneNumber;
    private String address;
    private String accessToken;
    @OneToOne(mappedBy = "user")
    private Cart cart;
    @OneToMany(mappedBy = "buyer")
    private List<Order> buyingList;
    @OneToMany(mappedBy = "seller")
    private List<Order> sellingList;
    @OneToMany(mappedBy = "user")
    private List<Product> productList;
    @OneToMany(mappedBy = "buyer")
    private List<Review> reviewList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", cart=" + cart +
                '}';
    }
}
