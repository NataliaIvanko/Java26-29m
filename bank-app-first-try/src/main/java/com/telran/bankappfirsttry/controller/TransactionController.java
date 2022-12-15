package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/{id}")
    public Transaction getTransactionByID(@PathVariable("id") Long id) {
        return transactionService.getTransactionByID(id);
    }
}
