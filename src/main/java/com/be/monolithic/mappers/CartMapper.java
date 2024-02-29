package com.be.monolithic.mappers;

import com.be.monolithic.dto.cart.CartDTO;
import com.be.monolithic.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "UUIDtoString")
    CartDTO CartToDTO(Cart cart);

    @Named("UUIDtoString")
    static String UUIDtoString(UUID uuid) {
        return uuid.toString();
    }
}
