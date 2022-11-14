package com.maveric.balanceservice.dto;

import com.maveric.balanceservice.enumeration.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
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

//    @NotEmpty(message = "Please enter currency")
    @Valid
    @NotNull(message = "Currency is mandatory INR/USD ")
    private Currency currency;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

