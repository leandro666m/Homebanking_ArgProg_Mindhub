package com.ap.homebanking.dto;
import com.ap.homebanking.models.ClientLoan;

// tabla intermedia para la relacion N-N
public class ClientLoanDTO{
    private Long id; //Id de los 2
    private Long loanId; //Id de los 2
    private String name;
    private double amount;
    private Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Long getLoanId(){
        return loanId;
    }
    public double getAmount(){
        return amount;
    }
    public Integer getPayments(){
        return payments;
    }
}