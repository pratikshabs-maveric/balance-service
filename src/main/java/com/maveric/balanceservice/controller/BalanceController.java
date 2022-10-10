package com.maveric.balanceservice.controller;

import com.maveric.balanceservice.dto.BalanceDto;
import com.maveric.balanceservice.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    // Create balance
    @PostMapping("accounts/{accountId}/balances")
    public ResponseEntity<BalanceDto> createBalance(@PathVariable String accountId, @Valid @RequestBody BalanceDto balanceDto) {
        BalanceDto BalanceDtoResponse = balanceService.createBalance(balanceDto);
        return new ResponseEntity<BalanceDto>(BalanceDtoResponse, HttpStatus.OK);
    }

    // Returns a list of balances
    @GetMapping("accounts/{accountId}/balances")
    public ResponseEntity<List<BalanceDto>> getBalances(@PathVariable String accountId, @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        List<BalanceDto> balanceDtoResponse = balanceService.getBalances(page,pageSize);
        return new ResponseEntity<List<BalanceDto>>(balanceDtoResponse, HttpStatus.OK);
    }

    // Returns balance details based on balance id.
    @GetMapping("accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<String> getBalanceDetails(@PathVariable String accountId,@PathVariable String balanceId) {
        BalanceDto BalanceDtoResponse = balanceService.getBalanceDetails(balanceId);
        return new ResponseEntity<String>(String.valueOf(BalanceDtoResponse.getAmount()), HttpStatus.OK);
    }


    @GetMapping("accounts/{accountId}")
    public ResponseEntity<String> getBalanceByAccountId(@PathVariable String accountId) {
        BalanceDto BalanceDtoResponse = balanceService.getBalanceByAccountId(accountId);
        return new ResponseEntity<String>(String.valueOf(BalanceDtoResponse.get_id()), HttpStatus.OK);
    }

    @PutMapping("accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<BalanceDto> updateBalance(@PathVariable String accountId,@PathVariable String balanceId,@RequestBody BalanceDto balanceDto) {
        BalanceDto balanceDtoResponse = balanceService.updateBalance(balanceId,balanceDto);
        return new ResponseEntity<BalanceDto>(balanceDtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("accounts/{accountId}/balances/{balancesId}")
    public ResponseEntity<String> deleteBalance(@PathVariable String accountId,@PathVariable String balancesId) {
        String result = balanceService.deleteBalance(balancesId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
