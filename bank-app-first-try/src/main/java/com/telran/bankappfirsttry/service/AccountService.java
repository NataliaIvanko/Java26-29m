package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {
    void createAccount(Account account);
    List<Account> getAccountsFiltered(List<String> city, LocalDateTime creationDate, String sort);
    ResponseEntity<Account> getAccountById(Integer userId);
    Account updateAccountById(Integer userId, Account account);
    Transaction transferMoneyBetweenAccounts(Integer idTo, Integer idFrom, Float amount, Account account);
 //   List<Account> getAccountsByFilters(String city);
    void deleteAccountByUserId(Integer userId);

  //  void transferMoneyBetweenAccounts(Integer fromId, Integer toId, Float amount, Account account);


//  Account findAccountById(Integer userId);
//  ResponseEntity<Account> findAccountById(Long userId);



}
