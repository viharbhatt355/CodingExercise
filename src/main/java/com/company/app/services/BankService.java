package com.company.app.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.errors.EntityExistsException;
import com.company.app.errors.EntityNotExistsException;
import com.company.app.errors.OperationFailedException;
import com.company.app.model.Account;
import com.company.app.model.Transaction;
import com.company.app.model.TxnType;
import com.company.app.repository.AccountRepository;
import com.company.app.repository.TransactionRepository;

@Service
@Transactional
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * @param t
     * @return
     */
    public Transaction handleTxn(Transaction t) {
        t.setTimestamp(new Date());
        if (TxnType.DEPOSIT.toString().equals(t.getTxnType())) {
            return handleDeposit(t);
        }

        else if (TxnType.WITHDRAW.toString().equals(t.getTxnType())) {
            return handleWithDraw(t);
        }
        else if (TxnType.TRANSFER.toString().equals(t.getTxnType())) {
            return handleTransfer(t);
        }
        else {
            throw new EntityNotExistsException(
                    "Unknown Txn type " + t.getTxnType() + " Allowed to use only: " + Arrays.deepToString(TxnType.values()));
        }

    }

    /**
     * @param t
     * @return
     */
    private Transaction handleTransfer(Transaction t) {

        Account toAcct = checkTOAccount(t);
        Account fromAcct = checkFROMAccount(t);

        if (!fromAcct.withdraw(t.getTxnAmount()))
            throw new OperationFailedException("Failed to Withdraw");
        if (!toAcct.deposit(t.getTxnAmount()))
            throw new OperationFailedException("Failed to deposit");

        return transactionRepository.save(t);

    }

    /**
     * @param t
     * @return
     */
    private Transaction handleWithDraw(Transaction t) {
        Account toAcct = checkTOAccount(t);
        if (!toAcct.withdraw(t.getTxnAmount()))
            throw new OperationFailedException("Failed to Withdraw");
        return transactionRepository.save(t);
    }

    private Transaction handleDeposit(Transaction t) {
        Account toAcct = checkTOAccount(t);
        if (!toAcct.deposit(t.getTxnAmount()))
            throw new OperationFailedException("Failed to deposit");
        return transactionRepository.save(t);
    }

    /**
     * @param t
     * @return
     */
    private Account checkTOAccount(Transaction t) {
        if (t.getToAccount() != null) {
            Optional<Account> exists = accountRepository.findById(t.getToAccount());

            if (exists.isPresent())
                return exists.get();
        }

        throw new EntityNotExistsException("Unknown TO account id +" + t.getToAccount());

    }

    private Account checkFROMAccount(Transaction t) {
        if (t.getFromAccount() != null) {
            Optional<Account> exists = accountRepository.findById(t.getFromAccount());

            if (exists.isPresent())
                return exists.get();
        }

        throw new EntityNotExistsException("Unknown From account id +" + t.getFromAccount());

    }

    // Account API

    /**
     * @return
     *
     */
    public Account[] getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();
        List<Account> sortedList = allAccounts.stream().sorted().collect(Collectors.toList());
        return sortedList.toArray(new Account[0]);
    }

    /**
     * @param id
     * @return
     */
    public Account getAccount(Long id) {
        Optional<Account> exists = accountRepository.findById(id);

        if (!exists.isPresent())
            throw new EntityNotExistsException("Unknown account id +" + id);
        return exists.get();
    }

    public void deleteAccount(Long id) {
        Optional<Account> exists = accountRepository.findById(id);

        if (!exists.isPresent())
            throw new EntityNotExistsException("Unknown account id +" + id);
        accountRepository.deleteById(id);
    }

    /**
     * @param t
     * @return
     */
    public Account saveAccount(Account t) {
        if (t.getAccountId() != null) {
            Optional<Account> exists = accountRepository.findById(t.getAccountId());

            if (exists.isPresent())
                throw new EntityExistsException("Duplicate account id +" + t.getAccountId());
        }
        return accountRepository.save(t);
    }

    public Account updateAccount(Account t) {
        Optional<Account> exists = accountRepository.findById(t.getAccountId());

        if (!exists.isPresent())
            throw new EntityNotExistsException("No such account id +" + t.getAccountId());
        Account dbAccount = exists.get();

        dbAccount.setOwner(t.getOwner());
        return dbAccount;
    }

}
