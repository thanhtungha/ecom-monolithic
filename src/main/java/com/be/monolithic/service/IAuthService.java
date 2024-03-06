package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.User;

import java.util.UUID;

public interface IAuthService {
    User register(AuRqRegisterArgs registerArgs);

    User login(AuRqLoginArgs loginArgs);

    void logout(String authorizationHeader);

    void changePassword(String authorizationHeader,
                           AuRqChangePasswordArgs changePasswordArgs);

    User update(String authorizationHeader, AuRqUpdateArgs updateArgs);

    User forgotPassword(AuRqForgotPwdArgs forgotPwdArgs);

    User getUser(String authorizationHeader);

    User getUser(UUID id);
}
