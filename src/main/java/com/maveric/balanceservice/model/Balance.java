package com.maveric.balanceservice.model;

import com.maveric.balanceservice.constant.Currency;
import com.maveric.balanceservice.constant.ErrorSuccessMessageConstants;
import com.maveric.balanceservice.validation.CurrencyValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;


@Getter
@Setter
@Document(collection = "balance")
public class Balance {

    @Id
    private String id;


    @NotBlank(message = ErrorSuccessMessageConstants.ACCOUNT_ID_NOT_BLANK)
    private String accountId;


    @NotBlank(message = ErrorSuccessMessageConstants.AMOUNT_NOT_BLANK)
    @Pattern(regexp = ErrorSuccessMessageConstants.AMOUNT_REGEX,message = ErrorSuccessMessageConstants.AMOUNT_INVALID)
    private String amount;

    @Enumerated(EnumType.STRING)
    @CurrencyValidation(anyOfTheseCurrency = {Currency.INR, Currency.EURO,Currency.DOLLAR})
    private Currency currency;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

}
