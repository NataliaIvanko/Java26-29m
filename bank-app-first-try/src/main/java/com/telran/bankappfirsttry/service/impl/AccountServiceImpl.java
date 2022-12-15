package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    public List<Account> getAccountsFiltered(String city, LocalDateTime creationDate) {//date, city, country, sorted by date asc, desc
       if (city != null) {
           if (creationDate == null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCity().equalsIgnoreCase(city))
                       .toList();
           }
       }
       if (city == null) {
           if (creationDate != null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCreationDate().equals(creationDate))
                       .toList();
           }
       }
       if (city != null) {
           if (creationDate != null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCity().equalsIgnoreCase(city)
                               && (acc.getCreationDate().equals(creationDate)))
                       .toList();
           }

       }
       return accountsMap.values().stream()
               .sorted(Comparator.comparing(Account::getUserId))
               .toList();
   }



    public ResponseEntity<Account> getAccountById(Integer userId) {
        Account account = accountsMap.get(userId);
        if(account == null){
            throw new ResponseStatusException(NOT_FOUND, "account is not found");
        }

        return ResponseEntity.ok(account);
    }

    @Override
    public Account updateAccountById( Integer userId, Account account) {
        Account newInfoAcc = accountsMap.get(userId);
        if(userId == null){
            throw new ResponseStatusException(NOT_FOUND);
        }
        newInfoAcc.setFirstName(account.getFirstName());
        newInfoAcc.setLastName(account.getLastName());
        newInfoAcc.setCountry(account.getCountry());
        newInfoAcc.setCity(account.getCity());
        newInfoAcc.setEmail(account.getEmail());

        return newInfoAcc;

    }

    @Override
    public void deleteAccountByUserId(Integer userId) {
        accountsMap.remove(userId);

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



       /*
       if (city == null && date == null) {
           return accountsMap.values().stream()
                   .sorted(Comparator.comparing(Account::getUserId))
                   .toList();
       }
       if (city != null && date == null) {
           return accountsMap.values().stream()
                   .filter(acc -> acc.getCity().equalsIgnoreCase(city))
                   .toList();
       }
       if (city != null) {
           return accountsMap.values().stream()

                   .filter(acc -> acc.getCity().equalsIgnoreCase(city)
                           && (acc.getCreationDate().equals(date)))
                   .toList();

       }
       //if date !=0 & sort != 0
            //if sort = -creation date
                // else
       return accountsMap.values().stream()
               .sorted(Comparator.comparing(acc-> acc.getCreationDate(), Comparator.reverseOrder()))
               .toList();

        */




}
