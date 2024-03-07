package com.be.monolithic.controller;

import com.be.monolithic.AbstractContainerBaseTest;
import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest extends AbstractContainerBaseTest {
    private static String BASE_API = "/api/auth";

    @Test
    @Order(0)
    void register() throws Exception {
        AuRqRegisterArgs registerArgs = new AuRqRegisterArgs("userName",
                "userPassword", "1234567890");
        String reqString = objectMapper.writeValueAsString(registerArgs);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        BASE_API + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void login() throws Exception {
        AuRqLoginArgs loginArgs = new AuRqLoginArgs("userName", "userPassword");
        String reqString = objectMapper.writeValueAsString(loginArgs);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        BASE_API + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //check response
    }

    @Test
    @Order(3)
    void logout() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        BASE_API + "/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + getAccessToken());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        //check response

        //check db
        Optional<User> optional = authRepository.findByUserName("userName");
        optional.ifPresentOrElse(user -> {
            if (!user.getAccessToken().isEmpty()) {
                fail("test case failed!");
            }
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void changePassword() throws Exception {
        AuRqChangePasswordArgs changePasswordArgs = new AuRqChangePasswordArgs(
                "newPassword");
        String reqString = objectMapper.writeValueAsString(changePasswordArgs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        BASE_API + "/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        //check response

        //check db
        Optional<User> optional = authRepository.findByUserName("userName");
        optional.ifPresentOrElse(user -> assertEquals(user.getUserPassword(),
                        changePasswordArgs.getNewPassword()),
                () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void update() throws Exception {
        AuRqUpdateArgs updateArgs = new AuRqUpdateArgs("0987654321",
                "new " + "Address");
        String reqString = objectMapper.writeValueAsString(updateArgs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                        BASE_API + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        //check response

        //check db
        Optional<User> optional = authRepository.findByUserName("userName");
        optional.ifPresentOrElse(user -> {
            assertEquals(user.getPhoneNumber(), updateArgs.getPhoneNumber());
            assertEquals(user.getAddress(), updateArgs.getAddress());
        }, () -> fail("test case failed!"));
    }

    @Test
    @Order(2)
    void forgotPassword() throws Exception {
        AuRqForgotPwdArgs updateArgs = new AuRqForgotPwdArgs("userName");
        String reqString = objectMapper.writeValueAsString(updateArgs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                        BASE_API + "/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .content(reqString);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    String getAccessToken() {
        Optional<User> optional = authRepository.findByUserName("userName");
        if (optional.isEmpty()) {
            fail("test case failed!");
        }
        return optional.get().getAccessToken();
    }
}