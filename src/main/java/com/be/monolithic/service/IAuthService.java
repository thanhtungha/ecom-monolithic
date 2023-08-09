package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IAuthService {
    UserInfo register(AuRqRegisterArgs registerArgs);

    UserInfo login(AuRqLoginArgs loginArgs);

    boolean logout(String authorizationHeader);

    boolean changePassword(String authorizationHeader, AuRqChangePasswordArgs changePasswordArgs);

    UserInfo update(String authorizationHeader, AuRqUpdateArgs updateArgs);

    ResponseEntity<?> forgotPassword(String authorizationHeader);

    boolean delete(String authorizationHeader);
    UserInfo getUserInfo(String authorizationHeader);
    UserInfo getUserInfo(UUID id);
}
