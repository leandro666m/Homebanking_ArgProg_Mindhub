package com.ap.homebanking.services;

import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {
    public Client findByEmail(String name);
    public List<ClientDTO> getClients();
    public ClientDTO getClient(@PathVariable Long id);
    public void save(Client newClientRegistered);

}
