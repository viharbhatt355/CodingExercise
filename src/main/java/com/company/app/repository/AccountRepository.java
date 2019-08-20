package com.company.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.app.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
