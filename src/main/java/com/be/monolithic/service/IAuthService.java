package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    UserInfo register(AuRqRegisterArgs registerArgs);

    UserInfo login(AuRqLoginArgs loginArgs);

    boolean logout(String userName);

    UserInfo changePassword(String userName, String password);

    UserInfo update(String userName, AuRqUpdateArgs updateArgs);

    ResponseEntity<?> forgotPassword(String userName);

    boolean delete(String userName);
}
