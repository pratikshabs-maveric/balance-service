package com.maveric.balanceservice.model;



import com.maveric.balanceservice.enumeration.Currency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document(collection = "BalanceDetails")
public class Balance {
    @Id
    private String _id;
    private String accountId;
    private Number amount;
    private Currency currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
