package com.company.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.app.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
