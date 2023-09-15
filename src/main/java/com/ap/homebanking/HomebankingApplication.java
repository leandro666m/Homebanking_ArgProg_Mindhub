package com.ap.homebanking;

import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class HomebankingApplication {
/*
	@Autowired
	private PasswordEncoder passwordEncoder;
*/
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository ) {
		return (args) -> {
/*
//----------CLIENTES
			Client client1 =new Client( "Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("pass1234") );
            Client client2 = new Client( "Jack","Sparrow","jacksparr@mindhub.com", passwordEncoder.encode("pass1234") );
			Client clientAdmin = new Client( "admin","admin","admin@mindhub.com", passwordEncoder.encode("admin1234") );
				clientRepository.save( client1 );
				clientRepository.save( client2 );
				clientRepository.save( clientAdmin );
//----------CUENTAS
            Account account1 = new Account();
					account1.setNumber("VIN001");
					account1.setCreationDate( LocalDate.now() );
					account1.setBalance(5000);
					account1.setIsActive(true);
			Account account2 = new Account();
					account2.setNumber("VIN002");
					account2.setCreationDate( LocalDate.now().plusDays(1) ) ;
					account2.setBalance(7000);
					account2.setIsActive(true);
			Account account3 = new Account();
					account3.setNumber("VIN003");
					account3.setCreationDate( LocalDate.now() ) ;
					account3.setBalance(15000);
					account3.setIsActive(true);
				client1.addAccount( account1);
				client1.addAccount( account2 );
				client2.addAccount( account3 );
					accountRepository.save( account1 );
					accountRepository.save( account2 );
					accountRepository.save( account3 );
//----------TRANSACCIONES
			Transaction trans1 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -2300, "Debito automatico");
			Transaction trans2 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 300, "Deposito");
			Transaction trans3 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -300, "Debito automatico");
			Transaction trans4 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 8300, "Pago");
				account1.addTransaction( trans1 );
				account1.addTransaction( trans2 );
				account2.addTransaction( trans3 );
				account3.addTransaction( trans4 );
					transactionRepository.save( trans1 );
					transactionRepository.save( trans2 );
					transactionRepository.save( trans3 );
					transactionRepository.save( trans4 );
//----------LOANS
            // -----------------------------name - amount - payments
			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz", 300000, List.of(6,12,24,36));
					loanRepository.save( loan1 );
					loanRepository.save( loan2 );
					loanRepository.save( loan3 );

           ClientLoan clientLoan1 = new ClientLoan(400000.0, 60);
		   ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);
		   ClientLoan clientLoan3 = new ClientLoan(100000.0, 24);
		   ClientLoan clientLoan4 = new ClientLoan(200000.0, 36);
			loan1.addClientLoans( clientLoan1 ); //viceversa: al prestamo1 le asigno el cliente1
			loan2.addClientLoans( clientLoan2 );
			loan2.addClientLoans( clientLoan3 );
			loan3.addClientLoans( clientLoan4 );
				client1.addClientLoans( clientLoan1 ); // asigno el Prestamo1 al cliente1
				client1.addClientLoans( clientLoan2 );
				client2.addClientLoans( clientLoan3 );
				client2.addClientLoans( clientLoan4 );
					clientLoanRepository.save( clientLoan1 ); // se guarda
					clientLoanRepository.save( clientLoan2 );
					clientLoanRepository.save( clientLoan3 );
					clientLoanRepository.save( clientLoan4 );
//---CARDS
			//-- card1
			Card card1 = new Card( );
			card1.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card1.setType(CardType.DEBIT);
			card1.setColor(CardColor.GOLD);
			card1.setNumber("1234 4567 9876 6543");
			card1.setCvv((short) 003);
			card1.setFromDate(LocalDate.now() );
			card1.setThruDate(LocalDate.now().plusYears(5) );
			card1.setIsActive(true);
				// a client1 Melba
				client1.addCard(card1);
				cardRepository.save(card1);
			//-- card2
			Card card2 = new Card();
			card2.setCardHolder(client1.getFirstName()+" "+client1.getLastName());
			card2.setType(CardType.CREDIT);
			card2.setColor(CardColor.TITANIUM);
			card2.setNumber("6543 1234 9876 8888");
			card2.setCvv((short) 654);
			card2.setFromDate(LocalDate.now() );
			card2.setThruDate(LocalDate.now().plusYears(5) );
			card2.setIsActive(true);
				// a client1 Melba
				client1.addCard(card2);
				cardRepository.save(card2);
			//-- card3
			Card card3 = new Card( );
			card3.setCardHolder(client2.getFirstName()+" "+client1.getLastName());
			card3.setType(CardType.CREDIT);
			card3.setColor(CardColor.SILVER);
			card3.setNumber("0011 7777 6666 3215");
			card3.setCvv((short) 654);
			card3.setFromDate(LocalDate.now() );
			card3.setThruDate(LocalDate.now().plusYears(5) );
			card3.setIsActive(true);
				// a client2
				client2.addCard(card3);
				cardRepository.save(card3);
*/
		};
	}
}
