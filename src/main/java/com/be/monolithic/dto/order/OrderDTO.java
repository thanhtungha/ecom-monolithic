package com.be.monolithic.dto.order;

import com.be.monolithic.dto.auth.UserDTO;
import com.be.monolithic.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String id;
    private Date createDate;
    private Date updateDate;
    private UserDTO owner;
    private List<OrderItemDTO> orderItems;
}
