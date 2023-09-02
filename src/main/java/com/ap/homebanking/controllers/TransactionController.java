package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
     private TransactionRepository transactionRepository;

    @GetMapping("/transaction")
    public Set<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }
    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        return new TransactionDTO( transactionRepository.findById(id).orElse(null) );
    }


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,  @RequestParam Double amount, @RequestParam String description,
                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {

        Client clientLogged = clientRepository.findByEmail(authentication.getName());
        Account accountOrigin = accountRepository.getAccountByNumber(fromAccountNumber);
        Account accountDestination = accountRepository.getAccountByNumber(toAccountNumber);

        //Verificar que exista la cuenta de destino
        if (!accountRepository.existsByNumber(toAccountNumber)) {
            return new ResponseEntity<>("No existe la cuenta destino.", HttpStatus.FORBIDDEN); }
        // Verificar que la cuenta de origen tenga el monto disponible.
        if (amount > accountOrigin.getBalance()) {
            return new ResponseEntity<>("Fondos insuficientes.", HttpStatus.FORBIDDEN); }
        //Verificar que los parámetros no estén vacíos
        if (amount == null || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Completar todos los datos.", HttpStatus.FORBIDDEN); }
        //Verificar que exista la cuenta de origen
        if (!accountRepository.existsByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("La cuenta origen no existe.", HttpStatus.FORBIDDEN); }
        //Verificar que la cuenta de origen pertenezca al cliente autenticado
        if (!clientLogged.getAccounts().stream().anyMatch(account -> account.getNumber().equals(fromAccountNumber))) {
            return new ResponseEntity<>("Cuenta origen no valida", HttpStatus.FORBIDDEN); }
        //Verificar que los números de cuenta no sean iguales
        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("No puede transferir a la misma cuenta.", HttpStatus.FORBIDDEN); }

        //transaccion de DEBITO a la cuenta origen
        Transaction debitTransaction = new Transaction(  TransactionType.DEBIT, LocalDateTime.now(), amount, description);
            accountOrigin.addTransaction( debitTransaction );
            accountOrigin.setBalance( accountOrigin.getBalance() - amount );
        transactionRepository.save( debitTransaction);
        accountRepository.save( accountOrigin );

        //transaccion de DEBITO a la cuenta origen
        Transaction creditTransaction =new Transaction(  TransactionType.DEBIT, LocalDateTime.now(), amount, description);
            accountDestination.addTransaction(creditTransaction);
            accountDestination.setBalance(accountDestination.getBalance() + amount);
        transactionRepository.save( creditTransaction );
        accountRepository.save( accountDestination );

        return new ResponseEntity<>( "Transferencia realizada.", HttpStatus.CREATED );
    }

}