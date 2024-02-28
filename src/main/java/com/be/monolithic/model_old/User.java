package com.be.monolithic.model_old;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class User extends BaseModel {
    private String userName;
    private String userPassword;
    private String phoneNumber;
    private String address;
    private String accessToken;
}
