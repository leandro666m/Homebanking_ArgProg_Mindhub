package com.ap.homebanking.services.implement;

import com.ap.homebanking.models.ClientLoan;
import com.ap.homebanking.repositories.ClientLoanRepository;
import com.ap.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientLoanImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void save(ClientLoan newclientLoan){
        clientLoanRepository.save( newclientLoan);
    }



}
