package com.ap.homebanking;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			ClientRepository clientRepository, AccountRepository accountRepository,
			TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
		return (args) -> {
//----------CLIENTES
			Client client1 =new Client( "Melba", "Morel","melba@mindhub.com" );
            Client client2 = new Client( "Jack","Sparrow","jacksparr@mindhub.com");

            clientRepository.save( client1 );
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

            Transaction trans3 = new Transaction( TransactionType.DEBIT, LocalDateTime.now(), -300, "Debito automatico");
            account2.addTransaction( trans3 );
            transactionRepository.save( trans3 );

            Transaction trans4 = new Transaction( TransactionType.CREDIT, LocalDateTime.now(), 8300, "Pago");
			account3.addTransaction( trans4 );
			transactionRepository.save( trans4 );

//----------LOANS
            // -----------------------------name - amount - payments
			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60));
			loanRepository.save( loan1 );
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			loanRepository.save( loan2 );
			Loan loan3 = new Loan("Automotriz", 300000, List.of(6,12,24,36));
			loanRepository.save( loan3 );

            //Créditos para Melba y segundo client
        // para el cliente 1 -Melba
            /*  Préstamo Hipotecario, 400.000, 60 cuotas.
                Préstamo Personal, 50.000, 12 cuotas */
            ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, client1, loan1);
        client1.addClientLoans( clientLoan1 ); // asigno el Prestamo1 al cliente1
            loan1.addClientLoans( clientLoan1 ); //viceversa: al prestamo1 le asigno el cliente1
            clientLoanRepository.save( clientLoan1 ); // se guarda

            ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, client1, loan2);
        client1.addClientLoans( clientLoan2 );
            loan2.addClientLoans( clientLoan2 );
            clientLoanRepository.save( clientLoan2 );
        // para el cliente 2
            /*  Préstamo Personal, 100.000, 24 cuotas
                Préstamo Automotriz, 200.000, 36 cuotas */
            ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, client2, loan2);
        client2.addClientLoans( clientLoan3 );
            loan2.addClientLoans( clientLoan3 );
            clientLoanRepository.save( clientLoan3 );

            ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, client2, loan3);
        client2.addClientLoans( clientLoan4 );
            loan3.addClientLoans( clientLoan4 );
            clientLoanRepository.save( clientLoan4 );

		};
	}



}
