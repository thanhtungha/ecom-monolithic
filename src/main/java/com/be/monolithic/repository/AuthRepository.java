package com.be.monolithic.repository;

import com.be.monolithic.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findById(UUID uuid);
    Optional<UserInfo> findByUserName(String userName);
    Optional<UserInfo> findByAccessToken(String accessToken);
}

