package com.company.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.app.errors.EntityNotExistsException;
import com.company.app.model.Transaction;
import com.company.app.repository.TransactionRepository;
import com.company.app.services.BankService;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    private BankService service;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Transaction[] findAll() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<Transaction> sortedList = allTransactions.stream().sorted().collect(Collectors.toList());
        return sortedList.toArray(new Transaction[0]);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Transaction findById(@PathVariable Long id) {
        Optional<Transaction> exists = transactionRepository.findById(id);

        if (!exists.isPresent())
            throw new EntityNotExistsException("Unknown txn id +" + id);
        return exists.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Transaction newTxn(@RequestBody Transaction t) {
        return service.handleTxn(t);
    }

    // Create accounts

}
