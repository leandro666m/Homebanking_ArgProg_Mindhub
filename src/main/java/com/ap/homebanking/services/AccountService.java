package com.ap.homebanking.services;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

public interface AccountService {
    List<AccountDTO> getAccounts();
    AccountDTO getAccount(@PathVariable Long id);
    boolean existsByNumber( String accountNumber );
    void save( Account accountNew );
     Account getAccountByNumber( String accountNumber);
    void delete(Client client, String number);
}
