package com.company.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.app.model.Account;
import com.company.app.services.BankService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private BankService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Account[] findAll() {
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Account findById(@PathVariable(value = "id") Long id) {
        return service.getAccount(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable(value = "id") Long id) {
        service.deleteAccount(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Account createAccount(@RequestBody Account t) {
        return service.saveAccount(t);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Account updateAccount(@PathVariable(value = "id") Long id, @RequestBody Account t) {
        t.setAccountId(id);
        return service.updateAccount(t);
    }

}
