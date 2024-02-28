package com.be.monolithic.mappers;

import com.be.monolithic.dto.order.OrderDTO;
import com.be.monolithic.model_old.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "id", target = "id")
    OrderDTO OrderToDTO(Order order);
}
