package com.be.monolithic.repository;

import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findById(UUID uuid);
    Optional<Inventory> findBySeller(UserInfo seller);
}

