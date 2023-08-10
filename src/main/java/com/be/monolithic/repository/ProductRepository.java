package com.be.monolithic.repository;

import com.be.monolithic.model.Product;
import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(UUID uuid);
    Optional<Product> findByName(String productName);
    Optional<List<Product>> findBySeller(UserInfo seller);
}

