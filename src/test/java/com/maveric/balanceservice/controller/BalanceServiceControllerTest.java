package com.maveric.balanceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.balanceservice.constant.Currency;
import com.maveric.balanceservice.constant.AccountType;
import com.maveric.balanceservice.exception.BalanceNotFoundException;
import com.maveric.balanceservice.feignclient.AccountFeignService;
import com.maveric.balanceservice.model.Account;
import com.maveric.balanceservice.model.Balance;
import com.maveric.balanceservice.repository.BalanceRepository;
import com.maveric.balanceservice.service.BalanceService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(BalanceServiceController.class)
@Tag("Integration tests")
 class BalanceServiceControllerTest {

    private static final String API_V1_BALANCE = "/api/v1/accounts/4/balances";

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Mock
    private List<Account> account;

    @MockBean
    private BalanceService balanceService;

    @MockBean
    private BalanceRepository balanceRepository;

    @MockBean
    private AccountFeignService accountFeignService;

    @Test
    void shouldGetBalanceWhenRequestMadeToGetBalance() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        mvc.perform(get(API_V1_BALANCE+"/631061c4c45f78545a1ed042").header("userId",1))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    void shouldGetBalancesWhenRequestMadeToGetBalances() throws Exception {
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        mvc.perform(get(API_V1_BALANCE + "?page=0&pageSize=10").header("userId",1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldReturnInternalServerWhenDbReturnsErrorForGetBalances() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
       when(balanceService.getAllBalance("1",0,10)).thenThrow(new IllegalArgumentException());
        mvc.perform(get(API_V1_BALANCE+"?page=-1").header("userId",1))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }
    @Test
    void shouldDeleteBalanceWhenRequestMadeToDeleteBalance() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        mvc.perform(delete(API_V1_BALANCE+"/631061c4c45f78545a1ed042").header("userId",1))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void shouldReturnInternalServerWhenDbReturnsErrorForBalance() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        when(balanceService.getBalance(any(),any())).
                thenThrow(new BalanceNotFoundException("631061c4c45f78545a1ed042"));
        mvc.perform(get(API_V1_BALANCE+"/631061c4c45f78545a1ed04","1").
                        header("userId",1))
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    void shouldReturnInternalServerWhenDbReturnsErrorForDelete() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        when(balanceService.deleteBalance(any(),any())).
                thenThrow(new BalanceNotFoundException("631061c4c45f78545a1ed04"));
        mvc.perform(delete(API_V1_BALANCE+"/631061c4c45f78545a1ed04").header("userId",1))
                .andExpect(status().isNotFound())
                .andDo(print());

    }
    @Test
    void shouldUpdateBalanceWhenRequestMadeToUpdateBalance() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        mvc.perform(put(API_V1_BALANCE+"/631061c4c45f78545a1ed042").
                        header("userId",1).contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(getSampleBalance())))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    void shouldCreateBalanceWhenRequestMadeToCreateBalance() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).
                thenReturn(getSampleAccount());
        mvc.perform(post(API_V1_BALANCE).contentType(MediaType.APPLICATION_JSON).
                        header("userId",1).content(mapper.writeValueAsString(getSampleBalance())))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldThrowBadRequestWhenBalanceDetailsAreWrongForUpdate() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        Balance balance = new Balance();
        balance.setCurrency(Currency.INR);
        balance.setAccountId(null);
        balance.setAmount("200");
        mvc.perform(put(API_V1_BALANCE+"/631061c4c45f78545a1ed042").
                        header("userId",1).contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(balance)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldThrowBadRequestWhenBalanceDetailsAreWrongForCreate() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        Balance balance = new Balance();
        balance.setCurrency(Currency.INR);
        balance.setAccountId(null);
        balance.setAmount("200");
        mvc.perform(post(API_V1_BALANCE).header("userId",1).
                        contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(balance)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldReturnInternalServerWhenDbReturnsErrorForUpdate() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        when(balanceService.updateBalance(Mockito.any(Balance.class),eq("631061c4c45f78545a1ed042"))).
                thenThrow(new IllegalArgumentException());
        mvc.perform(post(API_V1_BALANCE+"/631061c4c45f78545a1ed042").
                        header("userId",1).contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(getSampleBalance())))
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }
    @Test
    void shouldReturnInternalServerWhenDbReturnsErrorForCreate() throws Exception{
        when(accountFeignService.getAccountsbyId("1")).thenReturn(getSampleAccount());
        when(balanceService.createBalanceForAccount
                (Mockito.any(Balance.class))).thenThrow(new IllegalArgumentException());
        mvc.perform(post(API_V1_BALANCE).header("userId",1).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(getSampleBalance())))
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }


    public Balance getSampleBalance(){

       Balance balance = new Balance();
       balance.setCurrency(Currency.INR);
       balance.setAccountId("4");
       balance.setAmount("200");
        return balance;
    }

    public ResponseEntity<List<Account>> getSampleAccount(){

        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setCustomerId("1");
        account.setAccountType(AccountType.CURRENT);
        Account account1 = new Account();
        account1.setCustomerId("2");
        account1.setAccountType(AccountType.CURRENT);

        accountList.add(account1);
        accountList.add(account);
        return ResponseEntity.status(HttpStatus.OK).body(accountList);
    }
}
