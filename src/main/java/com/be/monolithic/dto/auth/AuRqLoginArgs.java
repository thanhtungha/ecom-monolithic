package com.be.monolithic.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuRqLoginArgs {
    @NotNull
    private String userName;
    @NotNull
    private String userPassword;
}
