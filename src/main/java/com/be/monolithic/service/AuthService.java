package com.be.monolithic.service;

import com.be.monolithic.dto.auth.*;
import com.be.monolithic.mappers.AuthMapper;
import com.be.monolithic.model.UserInfo;
import com.be.monolithic.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    public void register(AuRqRegisterArgs registerArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void login(AuRqLoginArgs loginArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void logout() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void changePassword(AuRqChangePasswordArgs changePasswordArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void update(AuRqUpdateArgs updateArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void forgotPassword(AuRqForgotPwdArgs forgotPwdArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void delete(AuRqDeleteArgs deleteArgs) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public UserInfo getUser(String userName) {
        return authRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }
}
