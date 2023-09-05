package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.TransactionDTO;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.repositories.TransactionRepository;
import com.ap.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void save(Transaction creditTransaction){
        transactionRepository.save( creditTransaction );
    }

    @Override
    public Set<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    @Override
    public Transaction findById(Long id) {
            return transactionRepository.findById(id).orElse(null) ;
    }
}
