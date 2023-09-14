package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Account;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
   boolean existsByNumber(String number);
   Account getAccountByNumber(String number);
   Account findByNumberAndClient(String number, Client client);
}
