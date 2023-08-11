package com.be.monolithic.mappers;

import com.be.monolithic.dto.order.OrderDTO;
import com.be.monolithic.dto.product.PdRqRegisterArgs;
import com.be.monolithic.dto.product.ProductDTO;
import com.be.monolithic.model.Order;
import com.be.monolithic.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "id", target = "id")
    OrderDTO OrderToDTO(Order order);
}
