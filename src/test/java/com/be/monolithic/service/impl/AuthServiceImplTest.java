package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.auth.*;
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
        authService.register(registerArgs);
        Optional<User> createdUser = authRepository.findByUserName(
                registerArgs.getUserName());
        createdUser.ifPresentOrElse(user -> {
            assertEquals(registerArgs.getUserName(), user.getUserName());
            assertEquals(registerArgs.getUserPassword(),
                    user.getUserPassword());
            assertEquals(registerArgs.getPhoneNumber(), user.getPhoneNumber());
        }, () -> fail("Cannot find created user in database!"));
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
        authService.logout(bearerToken);
        Optional<User> createdUser = authRepository.findByAccessToken(
                bearerToken);
        if (createdUser.isPresent()) {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void changePassword() {
        AuRqChangePasswordArgs changePasswordArgs = new AuRqChangePasswordArgs(
                "newPassword");
        authService.changePassword(getAuthorizationHeader(),
                changePasswordArgs);
        Optional<User> createdUser =
                authRepository.findByUserNameAndUserPassword(
                        "userName", "newPassword");
        if (createdUser.isEmpty()) {
            fail("test case failed!");
        } else {
            //change user password to original password: "userPassword"
            changePasswordArgs.setNewPassword("userPassword");
            authService.changePassword(getAuthorizationHeader(),
                    changePasswordArgs);
        }
    }

    @Test
    @Order(2)
    void update() {
        AuRqUpdateArgs updateArgs = new AuRqUpdateArgs("0987654321",
                "new address");
        User userInfo = authService.update(getAuthorizationHeader(), updateArgs);
        if (userInfo != null) {
            assertEquals(updateArgs.getPhoneNumber(), userInfo.getPhoneNumber());
            assertEquals(updateArgs.getAddress(), userInfo.getAddress());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void forgotPassword() {
        AuRqForgotPwdArgs forgotPwdArgs = new AuRqForgotPwdArgs("userName");
        User user = authService.forgotPassword(forgotPwdArgs);
        if (user != null) {
            assertEquals("userPassword",user.getUserPassword());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(4)
    void delete() {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName",
         "userPassword");
        User user = authService.login(loginArgs);

        boolean result = authService.deleteUserData(user);
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