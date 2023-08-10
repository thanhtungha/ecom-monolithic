package com.be.monolithic.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IvRqUpdateProductArgs {
    private String id;
    private String name;
    private int price;
    private int quantity;
}
