package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getTransactionByID(Long id);
    List<Transaction> getTransactionsByFilters();

}
