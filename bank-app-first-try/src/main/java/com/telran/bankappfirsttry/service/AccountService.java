package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    void createAccount(Account account);
  //  ResponseEntity<Account> findAccountById(Long userId);

    ResponseEntity<Account> findAccountById(Integer userId);
  //  Account findAccountById(Integer userId);
    List<Account>findAllAccounts();
    Account updateAccountDataById(Long userId);
    void deleteAccountByUserId(Long userId);





}
