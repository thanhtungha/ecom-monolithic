package com.be.monolithic.repository;

import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Optional<ProductModel> findById(UUID uuid);
    Optional<ProductModel> findByName(String productName);
    Optional<List<ProductModel>> findBySeller(UserInfo seller);
}

