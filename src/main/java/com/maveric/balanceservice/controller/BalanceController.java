package com.maveric.balanceservice.controller;

import com.maveric.balanceservice.dto.BalanceDto;
import com.maveric.balanceservice.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/v1/accounts/{accountId}/balances")
@RestController
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    // Create balance
    public ResponseEntity<BalanceDto> createBalance(@PathVariable String accountId, @RequestBody BalanceDto balanceDto) {
        BalanceDto BalanceDtoResponse = balanceService.createBalance(balanceDto);
        return new ResponseEntity<BalanceDto>(BalanceDtoResponse, HttpStatus.OK);
    }

    public ResponseEntity<List<BalanceDto>> getBalances(@PathVariable String accountId, @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        List<BalanceDto> balanceDtoResponse = balanceService.getBalances(page,pageSize);
        return new ResponseEntity<List<BalanceDto>>(balanceDtoResponse, HttpStatus.OK);
    }

    @GetMapping("{balanceId}")
    public ResponseEntity<String> getTransactionDetails(@PathVariable String accountId,@PathVariable String balanceId) {
        BalanceDto BalanceDtoResponse = balanceService.getBalanceDetails(balanceId);
        return new ResponseEntity<String>(String.valueOf(BalanceDtoResponse.getAmount()), HttpStatus.OK);
    }

    @PutMapping("{balanceId}")
    public ResponseEntity<BalanceDto> updateBalance(@PathVariable String accountId,@PathVariable String balanceId,@RequestBody BalanceDto balanceDto) {
        BalanceDto balanceDtoResponse = balanceService.updateBalance(balanceId,balanceDto);
        return new ResponseEntity<BalanceDto>(balanceDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("{balancesId}")
    public ResponseEntity<String> deleteBalance(@PathVariable String accountId,@PathVariable String balancesId) {
        String result = balanceService.deleteBalance(balancesId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
