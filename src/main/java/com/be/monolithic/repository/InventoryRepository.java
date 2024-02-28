package com.be.monolithic.repository;

import com.be.monolithic.model_old.Inventory;
import com.be.monolithic.model_old.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByOwner(User owner);
}

