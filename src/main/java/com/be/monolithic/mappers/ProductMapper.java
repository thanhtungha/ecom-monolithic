package com.be.monolithic.mappers;

import com.be.monolithic.dto.product.ProductDTO;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "productName")
    Product RegisterArgsToProduct(PdRqRegisterArgs registerArgs);

    @Mapping(source = "id", target = "id")
    ProductDTO ProductToDTO(Product product);

    @Mapping(source = "id", target = "id")
    Product DTOToProduct(ProductDTO dto);
}
