package com.be.monolithic.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuRqRegisterArgs {
    private String userName;
    private String userPassword;
    private String phoneNumber;
}
