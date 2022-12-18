package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionByID(@PathVariable("id") Long id) {
        return transactionService.getTransactionByID(id);


    }
    @GetMapping("/transaction")
    public List<Transaction> getTransactionsFiltered(@RequestParam(value = "type", required = false) List<String> type,
                                                     @RequestParam(value = "date", required = false) LocalDateTime date,
                                                     @RequestParam(value = "sort", required = false) List<String> sort){
        return transactionService.getTransactionsByFilters(type, date, sort);
    }
}
