package com.maveric.balanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceDto {
    private String  _id;

    @NotEmpty(message = "Please enter account ID")
    private String accountId;

    @NotNull(message = "Please enter amount")
    private Number amount;

    @NotEmpty(message = "Please enter currency")
    private String currency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

