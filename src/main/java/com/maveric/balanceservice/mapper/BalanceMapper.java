package com.maveric.balanceservice.mapper;

import com.maveric.balanceservice.dto.BalanceDto;
import com.maveric.balanceservice.model.Balance;

import java.util.List;

public interface BalanceMapper {
    Balance map(BalanceDto balanceDto);

    BalanceDto map(Balance balance);

    List<Balance> map (List<BalanceDto> balances);
}
