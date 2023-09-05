package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RequestMapping( "/api")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;

    //GETs
    @RequestMapping( "/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }
    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    //POST
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register( @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Faltan datos.", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Ese email ya esta en uso ", HttpStatus.FORBIDDEN);
        }

        Client newClientRegistered = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.save(newClientRegistered);

        int accountNumber = new Random( ).nextInt( 99999999 - 1)+1;
        Account newAccountForNewClient = new Account( "VIN-"+accountNumber, LocalDate.now(), 0  );
        newClientRegistered.addAccount( newAccountForNewClient );
        accountService.save( newAccountForNewClient );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return new ClientDTO( clientService.findByEmail( authentication.getName() ) );
    }

}
