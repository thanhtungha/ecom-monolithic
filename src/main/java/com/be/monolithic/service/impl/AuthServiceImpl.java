package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.exception.RestExceptions;
import com.be.monolithic.mappers.AuthMapper;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.security.AuthenticationProvider;
import com.be.monolithic.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final AuthRepository authRepository;
    private final AuthenticationProvider authenticationProvider;
    private final AuthMapper authMapper;

    @Override
    public UserInfo register(AuRqRegisterArgs registerArgs) {
        Optional<UserInfo> storedModel =
                authRepository.findByUserName(registerArgs.getUserName());
        if (storedModel.isPresent()) {
            throw new RestExceptions.UserExisted();
        }

        UserInfo userInfo = authMapper.RegisterArgsToUserInfo(registerArgs);
        userInfo.setCreateDate(new Date());
        userInfo.setUpdateDate(new Date());
        userInfo.setAccessToken(authenticationProvider.createAccessToken(userInfo.getUserName()));
        authRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo login(AuRqLoginArgs loginArgs) {
        Optional<UserInfo> storedModel =
                authRepository.findByUserName(loginArgs.getUserName());
        if (storedModel.isPresent() && storedModel.get().getUserPassword().equals(loginArgs.getUserPassword())) {
            UserInfo userInfo = storedModel.get();
            userInfo.setAccessToken(authenticationProvider.createAccessToken(userInfo.getUserName()));
            userInfo.setUpdateDate(new Date());
            authRepository.save(userInfo);
            return userInfo;
        } else {
            throw new RestExceptions.NotFound("User not found or wrong " +
                    "password");
        }
    }

    @Override
    public boolean logout(String accessToken) {
        Optional<UserInfo> storedModel =
                authRepository.findByAccessToken(accessToken);
        if (storedModel.isPresent()) {
            UserInfo userInfo = storedModel.get();
            userInfo.setAccessToken("");
            userInfo.setUpdateDate(new Date());
            authRepository.save(userInfo);
            return true;
        } else {
            throw new RestExceptions.InternalServerError("Unknown user!");
        }
    }

    @Override
    public boolean changePassword(String accessToken, AuRqChangePasswordArgs changePasswordArgs) {
        Optional<UserInfo> storedModel =
                authRepository.findByAccessToken(accessToken);
        if (storedModel.isPresent()) {
            UserInfo userInfo = storedModel.get();
            if(userInfo.getUserPassword().equals(changePasswordArgs.getNewPassword())) {
                throw new RestExceptions.BadRequest("New password cannot be the same as the old password.");
            }
            userInfo.setUserPassword(changePasswordArgs.getNewPassword());
            userInfo.setUpdateDate(new Date());
            authRepository.save(userInfo);
            return true;
        } else {
            throw new RestExceptions.InternalServerError("Unknown user!");
        }
    }

    @Override
    public UserInfo update(String accessToken, AuRqUpdateArgs updateArgs) {
        Optional<UserInfo> storedModel =
                authRepository.findByAccessToken(accessToken);
        if (storedModel.isPresent()) {
            UserInfo userInfo = storedModel.get();
            userInfo.setAddress(updateArgs.getAddress());
            userInfo.setPhoneNumber(updateArgs.getPhoneNumber());
            userInfo.setUpdateDate(new Date());
            authRepository.save(userInfo);
            return userInfo;
        } else {
            throw new RestExceptions.InternalServerError("Unknown user!");
        }
    }

    @Override
    public ResponseEntity<?> forgotPassword(String accessToken) {
        return null;
    }

    @Override
    public boolean delete(String accessToken) {
        Optional<UserInfo> storedModel =
                authRepository.findByAccessToken(accessToken);
        if (storedModel.isPresent()) {
            authRepository.delete(storedModel.get());
            return true;
        } else {
            throw new RestExceptions.InternalServerError("Unknown user!");
        }
    }
}
