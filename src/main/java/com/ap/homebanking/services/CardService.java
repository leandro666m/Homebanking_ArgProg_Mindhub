package com.ap.homebanking.services;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.Client;

public interface CardService {
     void save(Card newCard);
     void delete(Client client, String number);
}
