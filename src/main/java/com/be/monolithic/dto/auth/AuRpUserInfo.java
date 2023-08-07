package com.be.monolithic.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuRpUserInfo {
    private UUID id;
    private Date createDate;
    private Date updateDate;
    private String phoneNumber;
    private String address;
    private String accessToken;
    private String refreshToken;
}
