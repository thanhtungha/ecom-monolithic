package com.be.monolithic.repository;

import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<List<Order>> findByOwner(UserInfo owner);
    Optional<Order> findByIdAndOwner(UUID uuid, UserInfo owner);
}

