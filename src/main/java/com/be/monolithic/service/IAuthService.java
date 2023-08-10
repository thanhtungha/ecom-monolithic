package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.Inventory;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IAuthService extends IBaseService {
    UserInfo register(AuRqRegisterArgs registerArgs, Inventory inventory);

    UserInfo login(AuRqLoginArgs loginArgs);

    boolean logout(String authorizationHeader);

    boolean changePassword(String authorizationHeader,
                           AuRqChangePasswordArgs changePasswordArgs);

    UserInfo update(String authorizationHeader, AuRqUpdateArgs updateArgs);

    ResponseEntity<?> forgotPassword(String authorizationHeader);

    UserInfo getUserInfo(String authorizationHeader);

    UserInfo getUserInfo(UUID id);
}
