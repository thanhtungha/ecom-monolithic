package com.be.monolithic.repository;

import com.be.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAuthRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByAccessToken(String accessToken);
    Optional<User> findByUserNameAndUserPassword(String userName, String userPassword);
}

