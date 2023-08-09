package com.be.monolithic.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdRqAddReviewArgs {
    private String productId;
    private int rating;
    private String review;
}
