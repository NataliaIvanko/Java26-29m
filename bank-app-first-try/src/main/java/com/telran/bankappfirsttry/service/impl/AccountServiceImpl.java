package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.TransactionType;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private final static AtomicInteger userId = new AtomicInteger();
    private final Map<Integer, Account> accountsMap = new HashMap<Integer, Account>();

    private final List<Transaction> transactionList = new ArrayList<>();

    List<String> cities = accountsMap.values().stream()
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
        if (account == null) {
            throw new ResponseStatusException(NOT_FOUND, "account is not found");
        }

        return ResponseEntity.ok(account);
    }

    @Override
    public Account updateAccountById(Integer userId, Account account, Float amount) {
        Account newInfoAcc = accountsMap.get(userId);
        if (userId == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        if (account.getFirstName() != null) {
            newInfoAcc.setFirstName(account.getFirstName());
        }
        if (account.getLastName() != null) {
            newInfoAcc.setLastName(account.getLastName());
        }
        if (account.getCountry() != null) {
            newInfoAcc.setCountry(account.getCountry());
        }
        if (account.getCity() != null) {
            newInfoAcc.setCity(account.getCity());
        }
        if (account.getEmail() != null) {
            newInfoAcc.setEmail(account.getEmail());
        }
        if (account.getBalance() != null) {
            newInfoAcc.setBalance(account.getBalance() + amount);
            Transaction transaction = new Transaction();
            transaction.setAccountTo(userId);
            transaction.setAccountFrom(userId);
            if (transaction.getAccountTo().equals(transaction.getAccountFrom()) && amount > 0) {
                transaction.setType(TransactionType.DEPOSIT);
            }
            if (transaction.getAccountTo().equals(transaction.getAccountFrom()) && amount < 0) {
                transaction.setType(TransactionType.WITHDRAW);
            }
            if (!transaction.getAccountTo().equals(transaction.getAccountFrom())) {
                transaction.setType(TransactionType.TRANSFER);
            }
            transaction.setAmount(amount);
            //   accountsMap.get(userId).addTransactionToList(transaction.getId());
            System.out.println(transaction);

        }
        return newInfoAcc;
    }

    @Override
    public List<Account> getAccountsFiltered(List<String> city, LocalDateTime creationDate, String sort) {//date, city, country, sorted by date asc, desc

        if (city != null && creationDate == null) {
            if (sort == null) {
                return accountsMap.values().stream()
                        .filter(acc -> city.contains(acc.getCity()))
                        // .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equals("creationDate")) {
                return accountsMap.values().stream()
                        .filter(acc -> city.contains(acc.getCity()))
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equals("-creationDate")) {
                return accountsMap.values().stream()
                        .filter(acc -> city.contains(acc.getCity()))
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .toList();
            }
        }
        if (city == null && creationDate != null) {
            if (sort == null) {
                return accountsMap.values().stream()
                        .filter(acc -> acc.getCreationDate().equals(creationDate))
                        //  .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .toList();
            }
            if (sort.equals("creationDate")) {
                return accountsMap.values().stream()
                        .filter(acc -> acc.getCreationDate().equals(creationDate))
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equals("-creationDate")) {
                return accountsMap.values().stream()
                        .filter(acc -> acc.getCreationDate().equals(creationDate))
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .toList();
            }
        }
        if (city != null)
            if (creationDate != null) {
                if (sort == null) {
                    return accountsMap.values().stream()
                            .filter(acc -> city.contains(acc.getCity())
                                    && (acc.getCreationDate().equals(creationDate)))
                            .toList();
                }
                if (sort.equals("creationDate")) {
                    return accountsMap.values().stream()
                            .filter(acc -> city.contains(acc.getCity())
                                    && (acc.getCreationDate().equals(creationDate)))
                            .sorted(Comparator.comparing(Account::getCreationDate))
                            .toList();
                }
                if (sort.equals("-creationDate")) {
                    return accountsMap.values().stream()
                            .filter(acc -> city.contains(acc.getCity())
                                    && (acc.getCreationDate().equals(creationDate)))
                            .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                            .toList();
                }
            }
        return accountsMap.values().stream()
                .sorted(Comparator.comparing(Account::getUserId))
                .toList();
    }


    @Override
    public void transferMoneyBetweenAccounts(Integer idTo, Integer idFrom, Float amount, Account account) {
        if (!isAccountPresent(idTo)) {
            throw new ResponseStatusException(NOT_FOUND, "destination account is not found");
        }
        if (!isAccountPresent(idFrom)) {
            throw new ResponseStatusException(NOT_FOUND, "source account is not found");
        }
        if (!isEnoughMoney(amount, account)) {
            throw new ResponseStatusException(BAD_REQUEST, "either the source or the destination account balance is lower than the transferred amount");
        }

        accountsMap.get(idTo).setBalance(accountsMap.get(idTo).getBalance() + amount);

        accountsMap.get(idFrom).setBalance(accountsMap.get(idFrom).getBalance() - amount);
        Transaction transaction = new Transaction();
        transaction.setAccountTo(idTo);
        transaction.setAccountFrom(idFrom);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        System.out.println(transaction);
  //      accountsMap.get(idTo).addTransactionToList(transaction.getId());
  //      accountsMap.get(idFrom).addTransactionToList(transaction.getId());


        //  return transaction;

    }

    private boolean isAccountPresent(Integer id) {
        return accountsMap.entrySet().stream()
                .anyMatch(acc -> acc.getKey().equals(id));
    }

    private boolean isEnoughMoney(Float amount, Account account) {
        return accountsMap.get(account.getUserId()).getBalance() >= amount;

    }


    @Override
    public void deleteAccountByUserId(Integer userId) {
        accountsMap.remove(userId);

    }


}



