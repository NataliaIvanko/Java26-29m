package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
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
                               //acc.getCity().equalsIgnoreCase(city.get(0)))
                    //   .sorted(Comparator.comparing(Account::getCreationDate))
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


   /*
•	transfers money between accounts with given ids
•	if the matching records exists and account has enough money to transfer, the response code is 200 and the empty response body
•	if there is no record(s) in the collection with the given id, the response code is 404 with error(s) explanation in response
•	if account has not enough money to transfer, the response code is 400 with error explanation in response

 */
    /*
    The PUT method requests that the state of the target resource be created or replaced with the state defined by the representation
     enclosed in the request message content." You could very well design an API so that PUT /users/1 HTTP/1.1 will create the user
      with ID 1 if it does not exist, and replace the user with ID 1 if it does.
     */

    @Override
    public Transaction transferMoneyBetweenAccounts(Integer idTo, Integer idFrom, Float amount, Account account) {
        if (!isAccountPresent(idTo)){
            throw new ResponseStatusException(NOT_FOUND, "destination account is not found");
        }
        if (!isAccountPresent(idFrom)){
            throw new ResponseStatusException(NOT_FOUND, "source account is not found");
        }
        if(!isEnoughMoney(amount)){
            throw new ResponseStatusException(BAD_REQUEST, "either the source or the destination account balance is lower than the transferred amount");
        }

            accountsMap.get(idTo).setBalance(accountsMap.get(idTo).getBalance()+amount);
            accountsMap.get(idFrom).setBalance(accountsMap.get(idFrom).getBalance()-amount);
            Transaction transaction = new Transaction();
            transaction.setAccountTo(idTo);
            transaction.setAccountFrom(idFrom);
            transaction.setType("transfer");
            transaction.setAmount(amount);
            accountsMap.get(idTo).addTransactionToList(transaction.getId());
            accountsMap.get(idFrom).addTransactionToList(transaction.getId());


        return transaction;

    }


    private boolean isAccountPresent(Integer id){
        return accountsMap.entrySet().stream()
                .anyMatch(acc->acc.getKey().equals(id));
    }
    private boolean isEnoughMoney(Float amount){

       if( accountsMap.get(userId).getBalance()>=amount){
           return true;
       }else{
           return  false;
       }
    }

        /*
       if (city != null) {
           if (date == null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCity().equalsIgnoreCase(city.toString()))
                      // .sorted(Comparator.comparing(Account::getCreationDate))
                       .toList();
           }
       }

       if (city == null) {
           if (date != null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCreationDate().equals(date))
                       .toList();
           }
       }
       if (city != null) {
           if (date != null) {
               return accountsMap.values().stream()
                       .filter(acc -> acc.getCity().equalsIgnoreCase(city.toString())
                               && (acc.getCreationDate().equals(date)))
                       .toList();
           }

       }
       return accountsMap.values().stream()
               .sorted(Comparator.comparing(Account::getUserId))
               .toList();
   }

 */





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
