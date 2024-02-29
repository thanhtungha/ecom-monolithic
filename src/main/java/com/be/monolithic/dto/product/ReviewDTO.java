package com.be.monolithic.dto.product;

import com.be.monolithic.dto.auth.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private int rate;
    private String review;
    private UUID buyerUUID;
    private UserInfoDTO reviewer;
}
