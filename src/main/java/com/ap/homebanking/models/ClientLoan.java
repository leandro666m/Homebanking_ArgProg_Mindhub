package com.ap.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double amount;
    private Integer payments;

    //-------Relacion N-1 con Cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    //-------Relacion N-1 con Loan
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan(){
    }
    public ClientLoan(Double amount, Integer payments){
        this.amount = amount;
        this.payments = payments;
    }

    public Long getId(){
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public void setPayments(Integer payments) {
        this.payments = payments;
    }

   public Client getClient() {
        return client;
    }
   public void setClient(Client client) {
        this.client = client;
    }

   public Loan getLoan() {
        return loan;
    }
   public void setLoan(Loan loan) {
        this.loan = loan;
    }

}
