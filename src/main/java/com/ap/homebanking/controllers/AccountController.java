package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.SecondaryTable;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RequestMapping( value = "/api")
@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping( "/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById( id )
                .map( AccountDTO::new )
                .orElse(null);
    }

    @RequestMapping( path ="/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
       Client clientLogged = clientRepository.findByEmail( authentication.getName() );
            ClientDTO clientDto = new ClientDTO( clientLogged );
            Set<AccountDTO> accounts= clientDto.getAccounts();

        if( (long) accounts.size() >= 3 ) {
            return new ResponseEntity<>("Ya tiene 3 cuentas.",HttpStatus.FORBIDDEN);
        }else {
            int random = new Random( ).nextInt( 99999999 - 1)+1;
            String  accountNumber;
            do{
                accountNumber = "VIN"+random;
            } while (accountRepository.existsByNumber( accountNumber ));

                Account accountNew = new Account( "VIN-"+accountNumber, LocalDate.now(), 0  );
                clientLogged.addAccount( accountNew );
                accountRepository.save( accountNew );
            return new ResponseEntity<>("Cuenta creada.",HttpStatus.CREATED);
        }
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getClientCurrentAccounts(Authentication authentication){
        Client clientLogged = clientRepository.findByEmail(authentication.getName());
        return clientLogged.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }
}
