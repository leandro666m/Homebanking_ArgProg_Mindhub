package com.ap.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    //constructores
        public Client() { }
        public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email =email;
    }

    //metodos propios
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

// Accounts
/*-------Relacion 1-N con Account-------------------------------------------------*/
@OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
private Set<Account> accounts = new HashSet<>();

    public void addAccount( Account account) {
    account.setClient(this);
    accounts.add( account );
}

    public Set<Account> getAccounts() {
        return accounts;
    }

//Loans
/*-------Relacion 1-N con Loan-------------------------------------------------*/
@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
private Set<ClientLoan> loans = new HashSet<>();

    public void addClientLoans(ClientLoan clientLoan){
    clientLoan.setClient(this);
    loans.add(clientLoan);
}

    public Set<ClientLoan> getClientLoans(){
        return loans;
    }

    //@JsonIgnore
    public List<Loan> getLoans(){
        return loans.stream().map(ClientLoan::getLoan).collect(Collectors.toList());
    }

// Cards
/*-------Relacion 1-N con Card-------------------------------------------------*/
@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
private Set<Card> cards = new HashSet<>();

    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }

    public Set<Card> getCards() {
    return cards;
}
}
