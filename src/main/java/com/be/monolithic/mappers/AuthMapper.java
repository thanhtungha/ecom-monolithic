package com.be.monolithic.mappers;

import com.be.monolithic.dto.auth.UserDTO;
import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "userName", target = "userName")
    UserInfo RegisterArgsToUserInfo(AuRqRegisterArgs registerArgs);

    @Mapping(source = "userName", target = "userName")
    UserInfo LoginArgsToUserInfo(AuRqLoginArgs loginArgs);

    @Mapping(source = "id", target = "id")
    UserDTO UserInfoToResponse(UserInfo userInfo);
}
