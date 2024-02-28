package com.be.monolithic.repository;

import com.be.monolithic.model_old.Cart;
import com.be.monolithic.model_old.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByOwner(User owner);
}

