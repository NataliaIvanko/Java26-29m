package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.TransactionType;
import com.telran.bankappfirsttry.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
   private final static AtomicInteger userId = new AtomicInteger();
    private final Map<Integer, Account> accountsMap = new HashMap<Integer, Account>();

    private final List<Transaction>transactionList = new ArrayList<>();

    List<String>cities = accountsMap.values().stream()
            .map(Account::getCity)
            .collect(Collectors.toList());



    @Override
    public void createAccount(Account account) {
        account.setCreationDate(LocalDateTime.now());
        account.setUserId(userId.incrementAndGet());
        accountsMap.put(account.getUserId(), account);


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
    public List<Account> getAccountsFiltered(List<String> city, LocalDateTime creationDate, String sort) {//date, city, country, sorted by date asc, desc

       if (city != null) {
           System.out.println(city);
           if (creationDate == null) {
               System.out.println(creationDate);
               return accountsMap.values().stream()
                       .filter(acc -> city.contains(acc.getCity()))

                  //  .sorted(Comparator.comparing(Account::getCreationDate,Comparator.reverseOrder()))
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
                       .filter(acc -> acc.getCity().equalsIgnoreCase(city.toString())
                               && (acc.getCreationDate().equals(creationDate)))
                       .toList();
           }

       }
       return accountsMap.values().stream()
               .sorted(Comparator.comparing(Account::getUserId))
               .toList();
   }

    @Override
    public void transferMoneyBetweenAccounts(Integer idTo, Integer idFrom, Float amount, Account account, Transaction transaction) {
        if (!isAccountPresent(idTo)){
            throw new ResponseStatusException(NOT_FOUND, "destination account is not found");
        }
        if (!isAccountPresent(idFrom)){
            throw new ResponseStatusException(NOT_FOUND, "source account is not found");
        }
        if(!isEnoughMoney(amount, account)){
            throw new ResponseStatusException(BAD_REQUEST, "either the source or the destination account balance is lower than the transferred amount");
        }

            accountsMap.get(idTo).setBalance(accountsMap.get(idTo).getBalance()+amount);

            accountsMap.get(idFrom).setBalance(accountsMap.get(idFrom).getBalance()-amount);
            transaction = new Transaction();
            transaction.setAccountTo(idTo);
            transaction.setAccountFrom(idFrom);
            transaction.setType(TransactionType.TRANSFER);
            transaction.setAmount(amount);
           accountsMap.get(idTo).addTransactionToList(transaction.getId());
           accountsMap.get(idFrom).addTransactionToList(transaction.getId());


      //  return transaction;

    }


    private boolean isAccountPresent(Integer id){
        return accountsMap.entrySet().stream()
                .anyMatch(acc->acc.getKey().equals(id));
    }
    private boolean isEnoughMoney(Float amount, Account account){
        return accountsMap.get(account.getUserId()).getBalance() >= amount;

    }







    @Override
    public void deleteAccountByUserId(Integer userId) {
        accountsMap.remove(userId);

    }


}
