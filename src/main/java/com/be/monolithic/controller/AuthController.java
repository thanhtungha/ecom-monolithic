package com.be.monolithic.controller;

import com.be.monolithic.dto.BaseResponse;
import com.be.monolithic.dto.auth.*;
import com.be.monolithic.exception.BaseException;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.AuthMapper;
import com.be.monolithic.model.User;
import com.be.monolithic.service.*;
import jakarta.validation.Valid;
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
    private final ICartService cartService;
    private final AuthMapper authMapper;

    @GetMapping(path = "/greeting")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> greeting() {
        logger.info("greeting");
        return ResponseEntity.ok(new BaseResponse("Hello! This is Auth " +
                "Service."));
    }

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody AuRqRegisterArgs registerArgs) {
        try {
            User user = authService.register(registerArgs);
            cartService.createCart(user);
            return new ResponseEntity<>(authMapper.UserToUserDTO(user), HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                throw ex;
            }
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@Valid @RequestBody AuRqLoginArgs loginArgs) {
        try {
            User user = authService.login(loginArgs);
            return new ResponseEntity<>(authMapper.UserToUserDTO(user), HttpStatus.OK);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            authService.logout(authorizationHeader);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody AuRqChangePasswordArgs changePasswordArgs) {
        try {
            authService.changePassword(authorizationHeader, changePasswordArgs);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AuRqUpdateArgs updateArgs) {
        try {
            User user = authService.update(authorizationHeader,
                    updateArgs);
            return new ResponseEntity<>(authMapper.UserToUserDTO(user), HttpStatus.OK);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }

    @GetMapping(path = "/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> forgotPassword(@RequestBody AuRqForgotPwdArgs forgotPwdArgs) {
        try {
            User user = authService.forgotPassword(forgotPwdArgs);
            return new ResponseEntity<>(authMapper.UserToUserDTO(user), HttpStatus.OK);
        } catch (BaseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RestExceptions.InternalServerError(ex.getMessage());
        }
    }
}
