package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImplement implements com.ap.homebanking.services.ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client findByEmail(String name) {
        return clientRepository.findByEmail( name );
    }
    @Override
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }
    @Override
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById( id ).map( ClientDTO::new ).orElse(null);
    }
    @Override
    public void save(Client newClientRegistered) {
        clientRepository.save(newClientRegistered);
    }

}
