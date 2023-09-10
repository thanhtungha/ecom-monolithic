package com.be.monolithic.repository;

import com.be.monolithic.model.Order;
import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<List<Order>> findByOwner(User owner);
    Optional<Order> findByIdAndOwner(UUID uuid, User owner);
}

