package com.ap.homebanking.controllers;

import com.ap.homebanking.dto.AccountDTO;
import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping( value = "/api")
@RestController
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping ("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(Collectors.toList() );
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id){
        return transactionRepository.findById( id )
                .map(TransactionDTO::new )
                .orElse(null);
    }



}
