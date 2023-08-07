package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.auth.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final IAuthService authService;

    @PostMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        logger.info("greeting");
        return ResponseEntity.ok(new BaseResponse("Hello! This is Auth Service."));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AuRqRegisterArgs registerArgs) {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody AuRqLoginArgs loginArgs) {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody AuRqChangePasswordArgs changePasswordArgs) {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody AuRqUpdateArgs updateArgs) {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody AuRqForgotPwdArgs forgotPwdArgs) {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/delete-account")
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        try {
            //TODO: Implement here
            throw new RestExceptions.NotImplemented();
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}
