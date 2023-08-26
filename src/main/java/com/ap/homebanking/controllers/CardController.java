package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.CardDTO;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

import static com.ap.homebanking.models.CardColor.*;
import static com.ap.homebanking.models.CardType.*;

@RequestMapping( "/api")
@RestController
public class CardController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,@RequestParam CardColor cardColor) {
        Client clientLogged = clientRepository.findByEmail(authentication.getName());
        ClientDTO clientDto = new ClientDTO( clientLogged );

        Set<CardDTO> cards= clientDto.getCards();

        int foundDebit=0;
        int foundCredit=0;

                    for (CardDTO card : cards) {
                        if ( cardType == card.getType() ) {
                            if ( cardType.equals(DEBIT) ) {
                                foundDebit++;
                            }else {
                                foundCredit++;
                            }
                            if (cardColor == card.getColor()) {
                                if (card.getColor().equals(SILVER)) {
                                    System.out.println("Ya tiene tarjeta SILVER");
                                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                                } else if (card.getColor().equals(GOLD)) {
                                    System.out.println("Ya tiene tarjeta GOLD");
                                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                                } else {
                                    System.out.println("Ya tiene tarjeta TITANIUM");
                                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                                }
                            }
                        }
                    }
                    System.out.println("Tiene "+foundDebit+" tarjetas de Debito ");

                if(foundDebit>=3){
                    System.out.println("Ya tiene 3 tarjetas de Débito");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                } else if (foundCredit>=3) {
                    System.out.println("Ya tiene 3 tarjetas de Crédito");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }



        // No tiene tarjeta del color pedido, se la puede Crear:
            Card newCard = new Card( );
            newCard.setCardHolder( clientLogged.getFirstName()+" "+clientLogged.getLastName());
            newCard.setType( cardType );
            newCard.setColor( cardColor );
            newCard.setNumber( new Random().nextInt(9999-1)+1+"-" + new Random().nextInt(9999-1)+1+"-"+ new Random().nextInt(9999-1)+1+"-"+ new Random().nextInt(9999-1)+1 );
            newCard.setCvv((short) (new Random().nextInt(999-1)+1 ) );
            newCard.setFromDate(LocalDate.now() );
            newCard.setThruDate(LocalDate.now().plusYears(5) );
            // al cliente loggeado
            clientLogged.addCard( newCard );
            cardRepository.save( newCard );

            return new ResponseEntity<>(HttpStatus.CREATED);
        }



}