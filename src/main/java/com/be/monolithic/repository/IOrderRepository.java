package com.be.monolithic.repository;

import com.be.monolithic.model.Order;
import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    Optional<List<Order>> findByBuyer(User buyer);
    Optional<Order> findByIdAndBuyer(UUID uuid, User buyer);
}

