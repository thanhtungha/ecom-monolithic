package com.be.monolithic.service.impl;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

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
        createdUser.ifPresent(user -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void changePassword() {
        AuRqChangePasswordArgs args = new AuRqChangePasswordArgs("newPassword");
        String bearerToken = getAuthorizationHeader();
        authService.changePassword(bearerToken, args);
        Optional<User> optional = authRepository.findByUserNameAndUserPassword(
                "userName", "newPassword");
        optional.ifPresentOrElse(user -> {
            //change user password to original password: "userPassword"
            //for next test case
            args.setNewPassword("userPassword");
            authService.changePassword(bearerToken, args);
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void update() {
        AuRqUpdateArgs args = new AuRqUpdateArgs("0987654321", "new address");
        String bearerToken = getAuthorizationHeader();
        User user = authService.update(bearerToken, args);
        if (user != null) {
            assertEquals(args.getPhoneNumber(), user.getPhoneNumber());
            assertEquals(args.getAddress(), user.getAddress());
        } else {
            fail("test case failed!");
        }
    }

    @Test
    @Order(2)
    void forgotPassword() {
        AuRqForgotPwdArgs args = new AuRqForgotPwdArgs("userName");
        User user = authService.forgotPassword(args);
        assertEquals("userPassword", user.getUserPassword());
    }

    @Test
    @Order(4)
    void delete() {
        AuRqLoginArgs args = new AuRqLoginArgs("userName", "userPassword");
        User user = authService.login(args);
        boolean result = authService.deleteUserData(user);

        assertTrue(result);

        Optional<User> optional = authRepository.findByUserName("userName");
        optional.ifPresent(value -> fail("test case failed!"));
    }

    String getAuthorizationHeader() {
        Optional<User> optional = authRepository.findByUserName("userName");
        if (optional.isEmpty()) {
            fail("test case failed!");
        }
        return "Bearer " + optional.get().getAccessToken();
    }
}