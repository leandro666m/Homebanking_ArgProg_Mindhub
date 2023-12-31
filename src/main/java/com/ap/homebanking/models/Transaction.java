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

// constructores
        public Transaction( TransactionType type, LocalDateTime date, double amount, String description) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
        public Transaction() {    }

// metodos propios
    public long getId() {
        return id;
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

// Account
/*-------Relacion N-1 con Account-------------------------------------------------*/
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="account_id")
private Account account;

    //@JsonIgnore
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

}
