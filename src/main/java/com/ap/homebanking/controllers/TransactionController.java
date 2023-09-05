package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.ClientService;
import com.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
     private TransactionService transactionService;

    //GETs
    @GetMapping("/transactions")
    public Set<TransactionDTO> getTransactions(){
        return transactionService.getTransactions();
    }
    @GetMapping("/transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        return new TransactionDTO(Objects.requireNonNull(transactionService.findById(id) ) );
    }

    // POST -- CREATE
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,  @RequestParam Double amount, @RequestParam String description,
                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber) {

        Client clientLogged = clientService.findByEmail(authentication.getName());
        Account accountOrigin = accountService.getAccountByNumber(fromAccountNumber);
        Account accountDestination = accountService.getAccountByNumber(toAccountNumber);

        //Verificar que exista la cuenta de destino
        if (!accountService.existsByNumber(toAccountNumber)) {
            return new ResponseEntity<>("No existe la cuenta destino.", HttpStatus.FORBIDDEN); }
        // Verificar que la cuenta de origen tenga el monto disponible.
        if (amount > accountOrigin.getBalance()) {
            return new ResponseEntity<>("Fondos insuficientes.", HttpStatus.FORBIDDEN); }
        //Verificar que los parámetros no estén vacíos
        if (amount == null || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Completar todos los datos.", HttpStatus.FORBIDDEN); }
        //Verificar que exista la cuenta de origen
        if (!accountService.existsByNumber(fromAccountNumber)) {
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
        transactionService.save( debitTransaction);
        accountService.save( accountOrigin );

        //transaccion de DEBITO a la cuenta origen
        Transaction creditTransaction =new Transaction(  TransactionType.DEBIT, LocalDateTime.now(), amount, description);
            accountDestination.addTransaction(creditTransaction);
            accountDestination.setBalance(accountDestination.getBalance() + amount);
        transactionService.save( creditTransaction );
        accountService.save( accountDestination );

        return new ResponseEntity<>( "Transferencia realizada.", HttpStatus.CREATED );
    }

}