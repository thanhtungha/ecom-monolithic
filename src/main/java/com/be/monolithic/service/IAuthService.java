package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> register(AuRqRegisterArgs registerArgs);

    ResponseEntity<?> login(AuRqLoginArgs loginArgs);

    ResponseEntity<?> logout();

    ResponseEntity<?> changePassword(AuRqChangePasswordArgs changePasswordArgs);

    ResponseEntity<?> update(AuRqUpdateArgs updateArgs);

    ResponseEntity<?> forgotPassword(AuRqForgotPwdArgs forgotPwdArgs);

    ResponseEntity<?> delete(AuRqDeleteArgs deleteArgs);

    UserInfo getUser(String userName);
}
