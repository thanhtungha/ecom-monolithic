package com.be.monolithic.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrRqUpdateOrderArgs {
    private String id;
    private List<ProductArgs> products;
}
