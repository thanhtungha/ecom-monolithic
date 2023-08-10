package com.be.monolithic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserInfo extends BaseModel {
    private String userName;
    private String userPassword;
    private String phoneNumber;
    private String address;
    private String accessToken;
    private String refreshToken;
    private boolean isActive = false;
}
