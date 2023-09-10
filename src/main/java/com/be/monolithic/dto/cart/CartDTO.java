package com.be.monolithic.dto.cart;

import com.be.monolithic.dto.auth.UserInfoDTO;
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
public class CartDTO {
    private String id;
    private Date createDate;
    private Date updateDate;
    private UserInfoDTO owner;
    private List<ProductDTO> products;
}
