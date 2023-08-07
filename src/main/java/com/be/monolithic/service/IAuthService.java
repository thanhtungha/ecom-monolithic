package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    UserInfo register(AuRqRegisterArgs registerArgs);

    UserInfo login(AuRqLoginArgs loginArgs);

    boolean logout(String accessToken);

    boolean changePassword(String accessToken, AuRqChangePasswordArgs changePasswordArgs);

    UserInfo update(String accessToken, AuRqUpdateArgs updateArgs);

    ResponseEntity<?> forgotPassword(String accessToken);

    boolean delete(String accessToken);
}
