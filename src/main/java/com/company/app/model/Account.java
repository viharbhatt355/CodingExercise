package com.company.app.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account implements Comparable<Account> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private BigDecimal balance;

    private String owner;

    private String accountType;

    public Account() {}

    /**
     * @param owner
     * @param string
     */
    public Account(String owner, String accountType) {
        this.owner = (owner);
        this.setAccountType(accountType);
    }

    @Override
    public int compareTo(Account o) {
        return (int) (this.getAccountId() - ((Account) o).getAccountId());
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean deposit(BigDecimal amt) {
        balance = balance.add(amt);
        return true;

    }

    public boolean withdraw(BigDecimal amtBD) {
        if (amtBD.compareTo(balance) <= 0) {
            balance = balance.subtract(amtBD);
            return true;
        }

        return false;
    }

}
