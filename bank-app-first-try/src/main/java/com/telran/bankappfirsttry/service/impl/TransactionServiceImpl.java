package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final List<Transaction> transactionsList = new ArrayList<>();
    @Override
    public ResponseEntity<Transaction> getTransactionByID(Long id) {

        Transaction transaction = null;
        for(Transaction tr : transactionsList){
            if(transaction.getId().equals(id)){
                return ResponseEntity.ok(tr);
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Transaction not found");
    }

    @Override
    public List<Transaction> getTransactionsByFilters(List<String> type, LocalDateTime date, List<String> sort) {
        return null;
    }



}
