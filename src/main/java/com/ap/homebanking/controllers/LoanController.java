package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.*;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import com.ap.homebanking.services.*;
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
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;

    //GETs
    @RequestMapping("/loans")
    public Set<LoanDTO> getLoans(){
        return loanService.getLoans();
    }
    @RequestMapping("/loan/{id}")
    public LoanDTO getLoan(@PathVariable Long id){
        return loanService.getLoan( id );
    }

    // POST -- CREATE
    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanAplicationDTO loanAplicationDTO){

        Client clientLogged = clientService.findByEmail(authentication.getName());
        Account accountDestination = accountService.getAccountByNumber( loanAplicationDTO.getToAccountNumber() );
        Loan loan = loanService.findById( loanAplicationDTO.getLoanId() );

        //Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if(loanAplicationDTO.getAmount()<=0 || loanAplicationDTO.getPayments()<=0 ){
            return new ResponseEntity<>("El monto debe ser mayor a cero.", HttpStatus.FORBIDDEN);
        }
        //Verificar que el préstamo exista
        if(loan==null){
            return new ResponseEntity<>("El préstamo no existe.", HttpStatus.FORBIDDEN);
        }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if(loan.getAmount()< loanAplicationDTO.getAmount()){
            return new ResponseEntity<>("Monto no permitido.", HttpStatus.FORBIDDEN);
        }
        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if( !(loan.getPayments().stream().anyMatch(pay -> pay.equals(loanAplicationDTO.getPayments() ))) ){
            return new ResponseEntity<>("Cantidad de cuotas no disponibles.", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino exista
        if( !accountService.existsByNumber(loanAplicationDTO.getToAccountNumber()) ){
            return new ResponseEntity<>("No existe la cuenta destino.", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if ( !clientLogged.getAccounts().stream().anyMatch( account -> account.getNumber().equals( loanAplicationDTO.getToAccountNumber() )) )  {
            return new ResponseEntity<>("La cuenta no pertenece al cliente.", HttpStatus.FORBIDDEN);
        }


//Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        ClientLoan newclientLoan = new ClientLoan( (loanAplicationDTO.getAmount()*1.2), loanAplicationDTO.getPayments() );
        clientLogged.addClientLoans( newclientLoan ); //ser agrega el nuevo prestamo al cliente loggeado
        loan.addClientLoans( newclientLoan ); //se agrega el nuevo prestamo a la tabla de prestamos

        clientLoanService.save(newclientLoan);  //se agrega el obj de la relacion Cliente-Prestamo a la tabla relacion

//Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo)
// con la descripción concatenando el nombre del préstamo y la frase “loan approved”
//Se debe actualizar la cuenta de destino sumando el monto solicitado.
            String description = loan.getName()+"-loan approved" ;
        Transaction creditTransaction = new Transaction( TransactionType.CREDIT, LocalDateTime.now() ,
                                                loanAplicationDTO.getAmount(), description );
        accountDestination.addTransaction( creditTransaction );
        transactionService.save( creditTransaction );


        return new ResponseEntity<>("Préstamo creado.", HttpStatus.CREATED);
    }

} //class