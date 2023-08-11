package com.be.monolithic.repository;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findById(UUID uuid);
    Optional<Cart> findByOwner(UserInfo owner);
}

