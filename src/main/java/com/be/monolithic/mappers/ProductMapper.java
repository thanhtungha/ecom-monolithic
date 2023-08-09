package com.be.monolithic.mappers;

import com.be.monolithic.dto.auth.AuRpUserInfo;
import com.be.monolithic.dto.product.PdRpProduct;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.ProductModel;
import com.be.monolithic.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
    ProductModel RegisterArgsToProductModel(PdRqRegisterArgs registerArgs);

    @Mapping(source = "id", target = "id")
    PdRpProduct ProductModelToResponse(ProductModel productModel);

    @Named("stringToUUID")
    static UUID stringToUUID(String stringId) {
        return UUID.fromString(stringId);
    }
}
