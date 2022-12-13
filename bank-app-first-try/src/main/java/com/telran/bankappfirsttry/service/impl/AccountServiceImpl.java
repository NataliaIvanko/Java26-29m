package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
    private final static AtomicInteger userId = new AtomicInteger();
    private final Map<Integer, Account> accountsMap = new HashMap<Integer, Account>();


    @Override
    public void createAccount(Account account) {
        account.setCreationDate(LocalDateTime.now());
        account.setUserId(userId.incrementAndGet());
        accountsMap.put(account.getUserId(), account);


    }
   @Override
    public List<Account> findAllAccounts() {
        return accountsMap.values().stream().toList();
    }

    public ResponseEntity<Account> findAccountById(Integer userId) {
        Account account = accountsMap.get(userId);
        if(account == null){
            throw new ResponseStatusException(NOT_FOUND, "account is not found");
        }

        return ResponseEntity.ok(account);
    }

/*
    @Override
    public Account findAccountById(Integer userId) {
        Account account = accountsMap.get(userId);
        if(account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return account;
    }

 */


    @Override
    public Account updateAccountDataById( Long userId) {
        Account account = accountsMap.get(userId);
        if(userId == null){
            throw new ResponseStatusException(NOT_FOUND);
        }
        account.setFirstName(account.getFirstName());

        return null;
    }

    @Override
    public void deleteAccountByUserId(Long userId) {
        accountsMap.remove(userId);

    }




}