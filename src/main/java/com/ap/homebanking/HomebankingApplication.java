package com.ap.homebanking;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData( ClientRepository clientRepository, AccountRepository accountRepository) {
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
			//firstAccount.setOwner( firstClient);


            Account secAccount = new Account( );
				secAccount.setNumber("VIN002" );
				secAccount.setCreationDate( today.plusDays(1) );
				secAccount.setBalance( 7500 );
			//secAccount.setOwner( firstClient);


			firstClient.addAccount( firstAccount);
			firstClient.addAccount( secAccount );

			accountRepository.save( firstAccount);
			accountRepository.save( secAccount);
		};
	}



}
