package com.ap.homebanking.dto;

public class LoanAplicationDTO {
    private long loanId; //id del prestamo automotriz, hipotecario, ...
    private double amount;
    private Integer payments;
    private String toAccountNumber;

    public long getLoanId() {
        return loanId;
    }
    public double getAmount() {
        return amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
