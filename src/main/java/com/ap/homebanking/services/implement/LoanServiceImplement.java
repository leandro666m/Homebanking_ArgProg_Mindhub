package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.LoanDTO;
import com.ap.homebanking.dto.LoanAplicationDTO;
import com.ap.homebanking.models.Loan;
import com.ap.homebanking.repositories.LoanRepository;
import com.ap.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanAplicationDTO loanAplicationDTO;

    @Override
    public Set<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan) ).collect(Collectors.toSet());
    }

    @Override
    public LoanDTO getLoan(@PathVariable Long id){
        return new LoanDTO( Objects.requireNonNull( loanRepository.findById(id).orElse(null)) );
    }

    @Override
    public Loan findById(Long id){
        return loanRepository.findById( id).orElse(null);
    }


}
