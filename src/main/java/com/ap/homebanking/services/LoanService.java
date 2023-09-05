package com.ap.homebanking.services;

import com.ap.homebanking.dto.LoanDTO;
import com.ap.homebanking.models.Loan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;
import java.util.Set;

public interface LoanService {
    public Set<LoanDTO> getLoans();
    public LoanDTO getLoan(@PathVariable Long id);
    public Loan findById(Long id);

}
