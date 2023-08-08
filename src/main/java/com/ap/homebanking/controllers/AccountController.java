package com.ap.homebanking.controllers;

import com.ap.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping( value = "/api")
@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
}
