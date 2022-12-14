package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    void createAccount(Account account);
    List<Account>findAllAccounts();
    ResponseEntity<Account> findAccountById(Integer userId);
    Account updateAccountById(Integer userId, Account account);
    void deleteAccountByUserId(Integer userId);




//  Account findAccountById(Integer userId);
//  ResponseEntity<Account> findAccountById(Long userId);



}
