package com.ap.homebanking.controllers;
import com.ap.homebanking.dto.CardDTO;
import com.ap.homebanking.dto.ClientDTO;
import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.services.AccountService;
import com.ap.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

import static com.ap.homebanking.models.CardColor.*;
import static com.ap.homebanking.models.CardType.*;
import static com.ap.homebanking.utils.CardUtil.*;

@RequestMapping( "/api")
@RestController
public class CardController {

    @Autowired
    private ClientRepository clientService;
    @Autowired
    private CardService cardService;
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardType cardType,@RequestParam CardColor cardColor) {

        Client clientLogged = clientService.findByEmail(authentication.getName());
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
                                    return new ResponseEntity<>("Ya tiene tarjeta SILVER",HttpStatus.FORBIDDEN);
                                } else if (card.getColor().equals(GOLD)) {
                                    return new ResponseEntity<>("Ya tiene tarjeta GOLD",HttpStatus.FORBIDDEN);
                                } else {
                                    return new ResponseEntity<>("Ya tiene tarjeta TITANIUM",HttpStatus.FORBIDDEN);
                                }
                            }
                        }
                    }
                if(foundDebit>=3){
                    return new ResponseEntity<>("Ya tiene 3 tarjetas de Débito",HttpStatus.FORBIDDEN);
                } else if (foundCredit>=3) {
                    return new ResponseEntity<>("Ya tiene 3 tarjetas de Crédito",HttpStatus.FORBIDDEN);
                }

        String numCard ="";
                do{
                    numCard = CardNumberGenerator();
                }while( accountService.existsByNumber( numCard ) );
        // No tiene tarjeta del color pedido, se la puede Crear:
            Card newCard = new Card( );
            newCard.setCardHolder( clientLogged.getFirstName()+" "+clientLogged.getLastName());
            newCard.setType( cardType );
            newCard.setColor( cardColor );
            newCard.setNumber( numCard );
            newCard.setCvv((short) (new Random().nextInt(999-1)+1 ) );
            newCard.setFromDate(LocalDate.now() );
            newCard.setThruDate(LocalDate.now().plusYears(5) );
            // al cliente loggeado
            clientLogged.addCard( newCard );
            cardService.save( newCard );

            return new ResponseEntity<>("Creado",HttpStatus.CREATED);
        }

    @PatchMapping(path = "/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam Long id) {

        Client clientLogged = clientService.findByEmail(authentication.getName());
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
                        return new ResponseEntity<>("Ya tiene tarjeta SILVER",HttpStatus.FORBIDDEN);
                    } else if (card.getColor().equals(GOLD)) {
                        return new ResponseEntity<>("Ya tiene tarjeta GOLD",HttpStatus.FORBIDDEN);
                    } else {
                        return new ResponseEntity<>("Ya tiene tarjeta TITANIUM",HttpStatus.FORBIDDEN);
                    }
                }
            }
        }
        if(foundDebit>=3){
            return new ResponseEntity<>("Ya tiene 3 tarjetas de Débito",HttpStatus.FORBIDDEN);
        } else if (foundCredit>=3) {
            return new ResponseEntity<>("Ya tiene 3 tarjetas de Crédito",HttpStatus.FORBIDDEN);
        }

        String numCard ="";
        do{
            numCard = CardNumberGenerator();
        }while( accountService.existsByNumber( numCard ) );
        // No tiene tarjeta del color pedido, se la puede Crear:
        Card newCard = new Card( );
        newCard.setCardHolder( clientLogged.getFirstName()+" "+clientLogged.getLastName());
        newCard.setType( cardType );
        newCard.setColor( cardColor );
        newCard.setNumber( numCard );
        newCard.setCvv((short) (new Random().nextInt(999-1)+1 ) );
        newCard.setFromDate(LocalDate.now() );
        newCard.setThruDate(LocalDate.now().plusYears(5) );
        // al cliente loggeado
        clientLogged.addCard( newCard );
        cardService.save( newCard );

        return new ResponseEntity<>("Creado",HttpStatus.CREATED);
    }


}