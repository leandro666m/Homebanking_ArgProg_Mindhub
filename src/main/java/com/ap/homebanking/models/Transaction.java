package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private TransactionType type;
    private LocalDateTime date;
    private double amount;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="accountOwner_id")
    private Account accountOwner;

        public Transaction(long id, TransactionType type, LocalDateTime date, double amount, String description) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
        public Transaction() {
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /*----------------------------------------------------------------------------*/
    //@JsonIgnore
    public Account getAccountOwner() {
        return accountOwner;
    }
    public void setAccountOwner(Account accountOwner) {
        this.accountOwner = accountOwner;
    }

}
