package com.be.monolithic.repository;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Product;
import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ICartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);
}

