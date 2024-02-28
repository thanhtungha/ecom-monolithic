package com.be.monolithic.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private UUID id;
    private Date createDate;
    private Date updateDate;
    private String name;
    private int price;
    private int quantity;
    private double rating;
    private UUID sellerUUID;
    private List<ReviewDTO> reviews;
}
