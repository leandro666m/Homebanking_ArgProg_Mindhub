package com.ap.homebanking.services.implement;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void save(Card newCard) {
        cardRepository.save( newCard);
    }

    @Override
    public void delete(Client client, String number) {
        Card card = cardRepository.findByNumberAndClient(number, client);
            card.setIsActive(false);
            cardRepository.save(card);

    }
}

