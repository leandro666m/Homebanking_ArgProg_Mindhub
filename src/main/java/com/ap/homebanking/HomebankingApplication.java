package com.ap.homebanking;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			ClientRepository clientRepository,
			AccountRepository accountRepository,
			TransactionRepository transactionRepository) {
		return (args) -> {
//----------CLIENTES
			Client client1 =new Client( "Melba", "Morel","melba@mindhub.com" );
			clientRepository.save( client1 );

            Client client2 = new Client( "Jack","Sparrow","jacksparr@mindhub.com");
			clientRepository.save( client2 );
//----------CUENTAS
            Account account1 = new Account("VIN001", LocalDate.now(), 5000 );
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500 );
			Account account3 = new Account("VIN003", LocalDate.now(), 15000 );

			client1.addAccount( account1);
			client1.addAccount( account2 );
				client2.addAccount( account3 );

			accountRepository.save( account1 );
			accountRepository.save( account2 );
			accountRepository.save( account3 );

//----------TRANSACCIONES
		//a cuenta 1
			Transaction trans1 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -2300, "Debito automatico");
			account1.addTransaction( trans1 );
			transactionRepository.save( trans1 );

			Transaction trans2 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 300, "Deposito");
			account1.addTransaction( trans2 );
			transactionRepository.save( trans2 );
		// a cuenta 2
			Transaction trans3 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -300, "Debito automatico");
			account2.addTransaction( trans3 );
			transactionRepository.save( trans3 );
		// a cuenta 3
			Transaction trans4 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 8300, "Pago");
			account3.addTransaction( trans4 );
			transactionRepository.save( trans4 );


		};
	}



}
