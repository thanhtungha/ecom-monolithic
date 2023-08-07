package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.service.IAuthService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceImplTest {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private IAuthService authService;

    @Test
    @Order(0)
    void register() {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                "userPassword", "0123456789");
        authService.register(registerArgs);
        Optional<UserInfo> createdUser =
                authRepository.findByUserName(registerArgs.getUserName());
        if (createdUser.isPresent()) {
            UserInfo user = createdUser.get();
            assertEquals(registerArgs.getUserName(), user.getUserName());
            assertEquals(registerArgs.getUserPassword(),
                    user.getUserPassword());
            assertEquals(registerArgs.getPhoneNumber(), user.getPhoneNumber());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(1)
    void login() {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        UserInfo userInfo = authService.login(loginArgs);
        if (userInfo != null) {
            assertEquals(loginArgs.getUserName(), userInfo.getUserName());
            assertEquals(loginArgs.getUserPassword(),
                    userInfo.getUserPassword());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void logout() {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        UserInfo userInfo = authService.login(loginArgs);
        boolean result = authService.logout(userInfo.getUserName());
        if (result) {
            Optional<UserInfo> createdUser =
                    authRepository.findByAccessToken(userInfo.getAccessToken());
            if (createdUser.isPresent()) {
                fail("test case failed!");
            }
            return;
        }
        fail("test case failed!");
    }

    @Test
    @Order(2)
    void changePassword() {
        UserInfo userInfo = authService.changePassword("userName",
                "newPassword");
        if (userInfo != null) {
            assertEquals("newPassword", userInfo.getUserPassword());
            authService.changePassword("userName", "userPassword");
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void update() {
        AuRqUpdateArgs updateArgs = new AuRqUpdateArgs("0987654321",
                "new " + "address");
        UserInfo userInfo = authService.update("userName", updateArgs);
        if (userInfo != null) {
            assertEquals(updateArgs.getPhoneNumber(),
                    userInfo.getPhoneNumber());
            assertEquals(updateArgs.getAddress(), userInfo.getAddress());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    void forgotPassword() {
        //TODO: do not implement in phase 1
        //fail("This feature is not implemented yet.");
    }

    @Test
    @Order(3)
    void delete() {
        boolean result = authService.delete("userName");
        if (result) {
            Optional<UserInfo> createdUser = authRepository.findByUserName(
                    "userName");
            if (createdUser.isPresent()) {
                fail("test case failed!");
            }
            return;
        }
        fail("test case failed!");
    }
}