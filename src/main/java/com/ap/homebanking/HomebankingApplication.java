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
			Client firstClient =new Client( "Melba", "Morel", "melba@mindhub.com");
            Client secClient = new Client( "Jack", "Sparrow", "jacks@mindhub.com");
			clientRepository.save( firstClient );
            clientRepository.save( secClient );

            LocalDate today = LocalDate.now();
            Account firstAccount = new Account( "VIN001" , today , 5000 );
            Account secAccount = new Account( "VIN002" , today.plusDays(1) , 7500 );
			 accountRepository.save( firstAccount);
			accountRepository.save( secAccount);


			firstClient.addAccount( firstAccount);
			firstClient.addAccount( secAccount );
		};
	}



}
