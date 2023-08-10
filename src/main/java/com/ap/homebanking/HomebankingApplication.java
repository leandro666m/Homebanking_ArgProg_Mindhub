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
			Client firstClient =new Client( );
				firstClient.setFirstName("Melba");
				firstClient.setLastName("Morel");
				firstClient.setEmail("melba@mindhub.com");

            Client secClient = new Client( );
				secClient.setFirstName("Jack");
				secClient.setLastName("Sparrow");
				secClient.setEmail("jacksparr@mindhub.com");
			clientRepository.save( firstClient );
			clientRepository.save( secClient );

            LocalDate today = LocalDate.now();
            Account firstAccount = new Account();
				firstAccount.setNumber("VIN001" );
				firstAccount.setCreationDate( today );
				firstAccount.setBalance( 5000 );

            Account secAccount = new Account( );
				secAccount.setNumber("VIN002" );
				secAccount.setCreationDate( today.plusDays(1) );
				secAccount.setBalance( 7500 );

		firstClient.addAccount( firstAccount);
		firstClient.addAccount( secAccount );

			accountRepository.save( firstAccount);
			accountRepository.save( secAccount);

			LocalDateTime todayTransaction = LocalDateTime.now();
			Transaction firstTransaction = new Transaction();
				firstTransaction.setType(TransactionType.DEBITO);
				firstTransaction.setAmount(-2300);
				firstTransaction.setDate( todayTransaction );
				firstTransaction.setDescription("Debito automatico");

			firstAccount.addTransaction( firstTransaction );
			transactionRepository.save( firstTransaction );

		};
	}



}
