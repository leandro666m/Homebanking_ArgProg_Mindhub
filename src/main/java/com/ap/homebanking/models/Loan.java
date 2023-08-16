package com.ap.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private double amount;

    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();

    //----CONSTRUCTORES
    public Loan() {
    }
    public Loan(String name, double amount, List<Integer> payments) {
        this.name = name;
        this.amount = amount;
        this.payments = payments;
    }

    //metodos propios
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

// Clients
//-----Relacion 1-N con Client-----------------------
@OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
private Set<ClientLoan> clients = new HashSet<>();

    public Set<ClientLoan> getClientLoans() {
        return clients;
    }

    public void addClientLoans(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clients.add(clientLoan);
    }

    //@JsonIgnore
    public List<Client> getClients(){
        return clients.stream().map(ClientLoan::getClient).collect(Collectors.toList());
    }
}
