package com.ap.homebanking.dto;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private TransactionType type;
    private LocalDateTime date;
    private double amount;
    private String description;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
    }
    public TransactionDTO() {   }

    public long getId() {
        return id;
    }
    public TransactionType getType() {
        return type;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }

}
