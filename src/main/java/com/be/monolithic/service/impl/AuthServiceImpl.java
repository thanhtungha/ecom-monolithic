package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.AuthMapper;
import com.be.monolithic.model.User;
import com.be.monolithic.repository.IAuthRepository;
import com.be.monolithic.security.AuthenticationProvider;
import com.be.monolithic.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IAuthRepository authRepository;
    private final AuthenticationProvider authenticationProvider;
    private final AuthMapper authMapper;

    @Override
    public User register(AuRqRegisterArgs registerArgs) {
        Optional<User> storedModel = authRepository.findByUserName(
                registerArgs.getUserName());
        if (storedModel.isPresent()) {
            throw new RestExceptions.Conflict("User existed");
        }

        User user = authMapper.RegisterArgsToUserInfo(registerArgs);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setAccessToken(authenticationProvider.createAccessToken(
                user.getUserName()));
        authRepository.save(user);
        return user;
    }

    @Override
    public User login(AuRqLoginArgs arg) {
        Optional<User> storedModel =
                authRepository.findByUserNameAndUserPassword(
                        arg.getUserName(), arg.getUserPassword());
        if (storedModel.isPresent()) {
            User user = storedModel.get();
            user.setAccessToken(authenticationProvider.createAccessToken(
                    user.getUserName()));
            user.setUpdatedAt(new Date());
            authRepository.save(user);
            return user;
        } else {
            throw new RestExceptions.NotFound(
                    "User not found or wrong password");
        }
    }

    @Override
    public void logout(String authorizationHeader) {
        Optional<User> storedModel = authRepository.findByAccessToken(
                extractAccessToken(authorizationHeader));
        storedModel.ifPresentOrElse(user -> {
            user.setAccessToken("");
            user.setUpdatedAt(new Date());
            authRepository.save(user);
        }, () -> {
            throw new RestExceptions.Forbidden("Invalid accessToken!");
        });
    }

    @Override
    public void changePassword(String authorizationHeader,
                               AuRqChangePasswordArgs changePasswordArgs) {
        Optional<User> storedModel = authRepository.findByAccessToken(
                extractAccessToken(authorizationHeader));
        storedModel.ifPresentOrElse(user -> {
            if (user.getUserPassword()
                    .equals(changePasswordArgs.getNewPassword())) {
                throw new RestExceptions.BadRequest(
                        "New password cannot be the same as the old password.");
            }
            user.setUserPassword(changePasswordArgs.getNewPassword());
            user.setUpdatedAt(new Date());
            authRepository.save(user);
        }, () -> {
            throw new RestExceptions.Forbidden("Invalid accessToken!");
        });
    }

    @Override
    public User update(String authorizationHeader, AuRqUpdateArgs updateArgs) {
        Optional<User> storedModel = authRepository.findByAccessToken(
                extractAccessToken(authorizationHeader));
        if (storedModel.isPresent()) {
            User user = storedModel.get();
            user.setAddress(updateArgs.getAddress());
            user.setPhoneNumber(updateArgs.getPhoneNumber());
            user.setUpdatedAt(new Date());
            authRepository.save(user);
            return user;
        } else {
            throw new RestExceptions.Forbidden("Invalid accessToken!");
        }
    }

    @Override
    public User forgotPassword(AuRqForgotPwdArgs forgotPwdArgs) {
        Optional<User> storedModel = authRepository.findByUserName(
                forgotPwdArgs.getUserName());
        if (storedModel.isPresent()) {
            return storedModel.get();
        } else {
            throw new RestExceptions.Forbidden("User not found!");
        }
    }

    @Override
    public User getUser(String authorizationHeader) {
        Optional<User> storedModel = authRepository.findByAccessToken(
                extractAccessToken(authorizationHeader));
        if (storedModel.isPresent()) {
            return storedModel.get();
        } else {
            throw new RestExceptions.Forbidden("Invalid accessToken!");
        }
    }

    @Override
    public User getUser(UUID id) {
        Optional<User> storedModel = authRepository.findById(id);
        if (storedModel.isPresent()) {
            return storedModel.get();
        } else {
            throw new RestExceptions.NotFound("UserId not found!");
        }
    }

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(
                "Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RestExceptions.Forbidden("Invalid accessToken!");
    }

    @Override
    public boolean deleteUserData(User user) {
        authRepository.delete(user);
        return true;
    }
}
