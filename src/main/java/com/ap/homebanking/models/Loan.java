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
    @Column(name="max_amount")
    private Double maxAmount;

    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();

    //-----Relacion 1-N con Client-----------------------
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clients = new HashSet<>();

    //----CONSTRUCTORES
    public Loan() {
    }
    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
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
        return maxAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

// Clients
    public void addClientLoans(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clients.add(clientLoan);
    }

    public Set<ClientLoan> getClientLoans() {
        return clients;
    }

    //@JsonIgnore
    public List<Client> getClients(){
        return clients.stream().map(ClientLoan::getClient).collect(Collectors.toList());
    }
}
