package com.ap.homebanking.dto;

import com.ap.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private Double maxAmount;
    private List<Integer> payments;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.maxAmount = loan.getAmount();
        this.payments = loan.getPayments();
    }
    public LoanDTO() {}

    public long getId() {
        return id;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
}
