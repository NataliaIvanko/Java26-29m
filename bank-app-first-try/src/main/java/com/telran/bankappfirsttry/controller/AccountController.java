package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/accounts")
    public List<Account> findAllAccounts(@RequestParam (value = "city", required = false) String city){
        return accountService.getAccountsFiltered(city);
    }


    @GetMapping("/accounts/{userId}")
    public ResponseEntity<Account> findAccountById(@PathVariable("userId") Integer userId) {
      return  accountService.findAccountById(userId);
    }

    @PatchMapping("/accounts/{userId}")
    public Account updateAccountById(@PathVariable("userId") Integer userId,
                                     @RequestBody Account account){
        return accountService.updateAccountById(userId, account);
    }
/*
    @GetMapping("/accounts")
    public List<Account> getAccountsByFilters(@RequestParam (value = "city") String city){
        return accountService.getAccountsByFilters(city);
    }

 */

//use "parameters as a part of a path" for mandatory parameters
//use "parameters as a query string" for optional parameters.
/*

    @GetMapping("/accounts/{userId}")
    public Account findAccountById(@PathVariable("userId") Integer userId) {
        return  accountService.findAccountById(userId);
    }

 */
}
