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
    public List<Account> findAllAccounts(){
        return accountService.findAllAccounts();
    }



    @GetMapping("/accounts/{userId}")
    public ResponseEntity<Account> findAccountById(@PathVariable Integer userId) {
      return  accountService.findAccountById(userId);
    }


/*

    @GetMapping("/accounts/{userId}")
    public Account findAccountById(@PathVariable("userId") Integer userId) {
        return  accountService.findAccountById(userId);
    }

 */
}
