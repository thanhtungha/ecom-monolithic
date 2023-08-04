package com.be.monolithic.controller;

import com.be.monolithic.repository.AuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang3.NotImplementedException;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest extends AbstractContainerBaseTest{

    private static final Logger logger =
            LoggerFactory.getLogger(AuthControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthRepository authRepository;

    @Test
    void register() {
        throw new NotImplementedException();
    }

    @Test
    void login() {
        throw new NotImplementedException();
    }

    @Test
    void logout() {
        throw new NotImplementedException();
    }

    @Test
    void changePassword() {
        throw new NotImplementedException();
    }

    @Test
    void update() {
        throw new NotImplementedException();
    }

    @Test
    void forgotPassword() {
        throw new NotImplementedException();
    }

    @Test
    void delete() {
        throw new NotImplementedException();
    }
}