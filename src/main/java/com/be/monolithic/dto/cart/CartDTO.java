package com.be.monolithic.dto.cart;

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
public class CartDTO {
    private String id;
    private Date createdAt;
    private Date updatedAt;
    private UserDTO owner;
    private List<ProductDTO> products;
}
