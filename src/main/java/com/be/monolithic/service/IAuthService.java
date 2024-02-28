package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model_old.User;

import java.util.UUID;

public interface IAuthService extends IBaseService {
    User register(AuRqRegisterArgs registerArgs);

    User login(AuRqLoginArgs loginArgs);

    boolean logout(String authorizationHeader);

    boolean changePassword(String authorizationHeader,
                           AuRqChangePasswordArgs changePasswordArgs);

    User update(String authorizationHeader, AuRqUpdateArgs updateArgs);

    User forgotPassword(AuRqForgotPwdArgs forgotPwdArgs);

    User getUserInfo(String authorizationHeader);

    User getUserInfo(UUID id);
}
