package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.AccountService;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;



    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @PostMapping("/accounts")
    public void createAccount(@RequestBody Account account){
        accountService.createAccount(account);

    }


    @GetMapping("/accounts/{userId}")
    public ResponseEntity<Account> findAccountById(@PathVariable("userId") Integer userId) {
        return  accountService.getAccountById(userId);
    }
    @GetMapping("/accounts")   //creationDate
    public List<Account> getAccountsFiltered(@RequestParam (value = "city", required = false) List<String>city,
                                             @RequestParam(value = "date", required = false) LocalDateTime creationDate,
                                             @RequestParam(value= "sort", required = false) String sort){
        return accountService.getAccountsFiltered(city, creationDate, sort);
    }

    @PatchMapping("/accounts/{userId}")
    public Account updateAccountById(@PathVariable("userId") Integer userId,
                                     @RequestParam(value = "amount", required = false) Float amount,
                                     @RequestBody Account account){
        return accountService.updateAccountById(userId, account, amount);
    }


//accounts?from=<fromId>&to=<toId>&amount=<moneyAmount>:
    @PutMapping("/accounts")
    public void transferMoneyBetweenAccounts(@RequestParam(value = "idFrom", required = true ) Integer idFrom,
                                             @RequestParam(value = "idTo", required = true) Integer idTo,
                                             @RequestParam(value = "amount", required = true) Float amount,
                                             @RequestBody Account account){
        accountService.transferMoneyBetweenAccounts(idFrom, idTo, amount, account);

    }


}
