package com.be.monolithic.mappers;

import com.be.monolithic.dto.auth.UserDTO;
import com.be.monolithic.dto.auth.UserInfoDTO;
import com.be.monolithic.dto.auth.AuRqLoginArgs;
import com.be.monolithic.dto.auth.AuRqRegisterArgs;
import com.be.monolithic.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "userName", target = "userName")
    User RegisterArgsToUserInfo(AuRqRegisterArgs registerArgs);

    @Mapping(source = "userName", target = "userName")
    User LoginArgsToUserInfo(AuRqLoginArgs loginArgs);

    @Mapping(source = "id", target = "id")
    UserInfoDTO UserToUserInfoDTO(User userInfo);

    @Mapping(source = "id", target = "id")
    UserDTO UserToUserDTO(User userInfo);
}
