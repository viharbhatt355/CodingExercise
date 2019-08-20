package com.company.app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Transaction implements Comparable<Transaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn
    private Long fromAccount;

    // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn
    private Long toAccount;

    private String txnType;

    private BigDecimal txnAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tradeTimestamp")
    private Date timestamp;

    public Transaction() {}

    @Override
    public int compareTo(Transaction o) {
        return (int) (this.getId() - ((Transaction) o).getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * @param txnType
     *            the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    /**
     * @return the txnAmount
     */
    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    /**
     * @param txnAmount
     *            the txnAmount to set
     */
    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }

}
