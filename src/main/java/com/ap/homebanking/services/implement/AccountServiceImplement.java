package com.ap.homebanking.services.implement;
import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(acc -> new AccountDTO(acc)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById( id ).map( AccountDTO::new ).orElse(null);
    }

    @Override
    public boolean existsByNumber( String accountNumber ){
        return accountRepository.existsByNumber( accountNumber);
    }

    @Override
    public void save(Account accountNew ){
        accountRepository.save( accountNew);
    }

    @Override
    public Account getAccountByNumber( String accountNumber){
        return accountRepository.getAccountByNumber( accountNumber );
    }

}
