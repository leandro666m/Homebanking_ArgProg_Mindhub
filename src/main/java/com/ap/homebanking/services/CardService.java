package com.ap.homebanking.services;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.Client;

public interface CardService {
    public void save(Card newCard);
    public void delete(Client client, String number);
}
