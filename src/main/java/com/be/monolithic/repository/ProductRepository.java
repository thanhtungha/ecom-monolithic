package com.be.monolithic.repository;

import com.be.monolithic.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Optional<ProductModel> findById(UUID uuid);
    Optional<ProductModel> findByName(String productName);
}

