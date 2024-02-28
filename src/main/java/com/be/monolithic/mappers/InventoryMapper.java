package com.be.monolithic.mappers;

import com.be.monolithic.dto.inventory.InventoryDTO;
import com.be.monolithic.dto.inventory.IvRqAddProductArgs;
import com.be.monolithic.dto.inventory.IvRqUpdateProductArgs;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.PdRqUpdateArgs;
import com.be.monolithic.model_old.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "UUIDtoString")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "products", target = "products")
    InventoryDTO InventoryToDTO(Inventory inventory);

    @Mapping(source = "name", target = "name")
    PdRqRegisterArgs GenerateRegisterArgs(IvRqAddProductArgs addProductArgs);

    @Mapping(source = "id", target = "id")
    PdRqUpdateArgs GenerateUpdateArgs(IvRqUpdateProductArgs updateProductArgs);

    @Named("UUIDtoString")
    static String UUIDtoString(UUID uuid) {
        return uuid.toString();
    }
}
