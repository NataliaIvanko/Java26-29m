package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactionsList = new ArrayList<>();
    @Override
    public Transaction getTransactionByID(Long id) {

        Transaction transaction = null;
        for(Transaction tr : transactionsList){
            if(transaction.getId().equals(id)){
                transaction = tr;
            }
        }
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByFilters() {
        return null;
    }
}
