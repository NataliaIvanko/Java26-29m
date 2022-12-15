package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Account;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {
    void createAccount(Account account);
    List<Account> getAccountsFiltered(String city, LocalDateTime date);
    ResponseEntity<Account> getAccountById(Integer userId);
    Account updateAccountById(Integer userId, Account account);
 //   List<Account> getAccountsByFilters(String city);
    void deleteAccountByUserId(Integer userId);




//  Account findAccountById(Integer userId);
//  ResponseEntity<Account> findAccountById(Long userId);



}
