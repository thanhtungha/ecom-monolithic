package com.be.monolithic.dto.inventory;

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
public class InventoryDTO {
    private String id;
    private Date createDate;
    private Date updateDate;
    private UserDTO seller;
    private List<ProductDTO> products;
}
