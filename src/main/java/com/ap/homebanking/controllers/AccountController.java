package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RequestMapping( value = "/api")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientRepository clientService;

    @RequestMapping( "/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }

    @RequestMapping( path ="/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
       Client clientLogged = clientService.findByEmail( authentication.getName() );
            ClientDTO clientDto = new ClientDTO( clientLogged );
            Set<AccountDTO> accounts= clientDto.getAccounts();

        if( (long) accounts.size() >= 3 ) {
            return new ResponseEntity<>("Ya tiene 3 cuentas.",HttpStatus.FORBIDDEN);
        }else {
            int random = new Random( ).nextInt( 99999999 - 1)+1;
            String  accountNumber;
            do{
                accountNumber = "VIN"+random;
            } while (accountService.existsByNumber( accountNumber ));

                Account accountNew = new Account( "VIN-"+accountNumber, LocalDate.now(), 0  );
                clientLogged.addAccount( accountNew );
                accountService.save( accountNew );
            return new ResponseEntity<>("Cuenta creada.",HttpStatus.CREATED);
        }
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getClientCurrentAccounts(Authentication authentication){
        Client clientLogged = clientService.findByEmail(authentication.getName());
        return clientLogged.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toSet());
    }
}
