package com.be.monolithic.service.impl;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.mappers.AuthMapper;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import com.be.monolithic.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    @Override
    public ResponseEntity<?> register(AuRqRegisterArgs registerArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> login(AuRqLoginArgs loginArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> logout() {
        return null;
    }

    @Override
    public ResponseEntity<?> changePassword(AuRqChangePasswordArgs changePasswordArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(AuRqUpdateArgs updateArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> forgotPassword(AuRqForgotPwdArgs forgotPwdArgs) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(AuRqDeleteArgs deleteArgs) {
        return null;
    }

    @Override
    public UserInfo getUser(String userName) {
        return authRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }
}
