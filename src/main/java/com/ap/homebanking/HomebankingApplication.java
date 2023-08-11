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
			Client client1 =new Client( );
				client1.setFirstName("Melba");
				client1.setLastName("Morel");
				client1.setEmail("melba@mindhub.com");

            Client client2 = new Client( );
				client2.setFirstName("Jack");
				client2.setLastName("Sparrow");
				client2.setEmail("jacksparr@mindhub.com");
			clientRepository.save( client1 );
			clientRepository.save( client2 );
//----------CUENTAS
            LocalDate today = LocalDate.now();
            Account account1 = new Account();
				account1.setNumber("VIN001" );
				account1.setCreationDate( today );
				account1.setBalance( 5000 );

            Account account2 = new Account( );
				account2.setNumber("VIN002" );
				account2.setCreationDate( today.plusDays(1) );
				account2.setBalance( 7500 );

			Account account3 = new Account( );
			account3.setNumber("VIN003" );
			account3.setCreationDate( today.plusDays(1) );
			account3.setBalance( 17500 );

		client1.addAccount( account1);
		client1.addAccount( account2 );
			client2.addAccount( account3 );

			accountRepository.save( account1);
			accountRepository.save( account2);
			accountRepository.save( account3);


//----------TRANSACCIONES
			Transaction trans1 = new Transaction( TransactionType.DEBITO, LocalDateTime.now(), -2300, "Debito automatico");
			account1.addTransaction( trans1 );
			transactionRepository.save( trans1 );

			Transaction trans2 = new Transaction( TransactionType.CREDITO, LocalDateTime.now(), 300, "Deposito");
			account1.addTransaction( trans2 );
			transactionRepository.save( trans1 );

			Transaction trans3 = new Transaction( TransactionType.DEBITO, LocalDateTime.now(), -300, "Debito automatico");
			account2.addTransaction( trans3 );
			transactionRepository.save( trans3 );

			Transaction trans4 = new Transaction( TransactionType.CREDITO, LocalDateTime.now(), 8300, "Pago");
			account3.addTransaction( trans4 );
			transactionRepository.save( trans4 );


		};
	}



}
