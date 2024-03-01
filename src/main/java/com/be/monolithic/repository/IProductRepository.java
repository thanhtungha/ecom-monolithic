package com.be.monolithic.repository;

import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByProductName(String productName);
    Optional<List<Product>> findByUser(User user);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.reviewList WHERE p.id = :uuid")
    Optional<Product> findByIdWithReviewList(UUID uuid);
}

