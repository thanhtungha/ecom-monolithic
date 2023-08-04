package com.be.monolithic.repository;

import com.be.monolithic.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUUID(UUID uuid);
    Optional<UserModel> findByUserName(String userName);
}

