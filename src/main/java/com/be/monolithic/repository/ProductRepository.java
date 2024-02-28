package com.be.monolithic.repository;

import com.be.monolithic.model_old.Product;
import com.be.monolithic.model_old.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByName(String productName);
    Optional<List<Product>> findBySeller(User seller);
}

