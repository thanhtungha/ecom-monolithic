package com.be.monolithic.mappers;

import com.be.monolithic.dto.cart.CartDTO;
import com.be.monolithic.dto.inventory.InventoryDTO;
import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model.Cart;
import com.be.monolithic.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "UUIDtoString")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "products", target = "products")
    CartDTO CartToDTO(Cart cart);

    @Named("UUIDtoString")
    static String UUIDtoString(UUID uuid) {
        return uuid.toString();
    }
}
