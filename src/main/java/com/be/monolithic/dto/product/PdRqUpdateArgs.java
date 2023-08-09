package com.be.monolithic.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdRqUpdateArgs {
    private String id;
    private String name;
    private int price;
    private int quantity;
}
