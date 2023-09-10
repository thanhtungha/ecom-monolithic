package com.be.monolithic.repository;

import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByOwner(User owner);
}

