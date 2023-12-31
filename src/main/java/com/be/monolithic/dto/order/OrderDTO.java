package com.be.monolithic.dto.order;

import com.be.monolithic.dto.auth.UserInfoDTO;
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
    private UserInfoDTO owner;
    private List<OrderItemDTO> orderItems;
}
