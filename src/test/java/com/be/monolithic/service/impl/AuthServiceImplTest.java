package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceImplTest extends AbstractContainerBaseTest {
    @Test
    @Order(0)
    void register() {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                "userPassword", "0123456789");
        Inventory inventory = new Inventory();
        inventory.setCreateDate(new Date());
        inventory.setUpdateDate(new Date());
        authService.register(registerArgs);
        Optional<User> createdUser = authRepository.findByUserName(
                registerArgs.getUserName());
        if (createdUser.isPresent()) {
            User user = createdUser.get();
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
        User userInfo = authService.login(loginArgs);
        if (userInfo != null) {
            assertEquals(loginArgs.getUserName(), userInfo.getUserName());
            assertEquals(loginArgs.getUserPassword(),
                    userInfo.getUserPassword());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(3)
    void logout() {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        authService.login(loginArgs);

        String bearerToken = getAuthorizationHeader();
        boolean result = authService.logout(bearerToken);
        if (result) {
            Optional<User> createdUser = authRepository.findByAccessToken(
                    bearerToken);
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
        AuRqChangePasswordArgs changePasswordArgs = new AuRqChangePasswordArgs(
                "newPassword");
        boolean result = authService.changePassword(getAuthorizationHeader(),
                changePasswordArgs);
        if (result) {
            Optional<User> createdUser = authRepository.findByUserName(
                    "userName");
            createdUser.ifPresent(info -> assertEquals("newPassword",
                    info.getUserPassword()));

            changePasswordArgs.setNewPassword("userPassword");
            authService.changePassword(getAuthorizationHeader(),
                    changePasswordArgs);
            return;
        }
        fail("test case failed!");
    }

    @Test
    @Order(2)
    void update() {
        AuRqUpdateArgs updateArgs = new AuRqUpdateArgs("0987654321",
                "new " + "address");
        User userInfo = authService.update(getAuthorizationHeader(),
                updateArgs);
        if (userInfo != null) {
            assertEquals(updateArgs.getPhoneNumber(),
                    userInfo.getPhoneNumber());
            assertEquals(updateArgs.getAddress(), userInfo.getAddress());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void forgotPassword() {
        AuRqForgotPwdArgs forgotPwdArgs = new AuRqForgotPwdArgs("userName");
        User userInfo = authService.forgotPassword(forgotPwdArgs);
        if (userInfo != null) {
            assertEquals("userPassword",userInfo.getUserPassword());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(4)
    void delete() {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        User userInfo = authService.login(loginArgs);

        boolean result = authService.deleteUserData(userInfo);
        if (result) {
            Optional<User> createdUser = authRepository.findByUserName(
                    "userName");
            if (createdUser.isPresent()) {
                fail("test case failed!");
            }
            return;
        }
        fail("test case failed!");
    }

    String getAuthorizationHeader() {
        Optional<User> createdUser = authRepository.findByUserName("userName");
        if (createdUser.isEmpty()) {
            fail("test case failed!");
        }
        return "Bearer " + createdUser.get().getAccessToken();
    }
}