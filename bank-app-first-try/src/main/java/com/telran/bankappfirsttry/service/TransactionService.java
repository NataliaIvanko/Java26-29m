package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Transaction;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
   ResponseEntity<Transaction> getTransactionByID(Long id);

    List<Transaction> getTransactionsByFilters(List<String> type, LocalDateTime date, List<String> sort);

}
