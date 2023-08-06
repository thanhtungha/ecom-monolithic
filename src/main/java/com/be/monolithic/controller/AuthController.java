package com.be.monolithic.controller;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AuRqRegisterArgs registerArgs) {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody AuRqLoginArgs loginArgs) {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody AuRqChangePasswordArgs changePasswordArgs) {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody AuRqUpdateArgs updateArgs) {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody AuRqForgotPwdArgs forgotPwdArgs) {
        throw new RestExceptions.NotImplemented();
    }

    @PostMapping(path = "/delete-account")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody AuRqDeleteArgs deleteArgs) {
        throw new RestExceptions.NotImplemented();
    }
}
