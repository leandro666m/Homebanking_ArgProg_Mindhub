package com.ap.homebanking.dto;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardColor;
import com.ap.homebanking.models.CardType;
import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private short cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private boolean isActive;

    //constructor
    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.isActive = card.getIsActive();
    }

    //metodos propios
    public Long getId() {
        return id;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public CardType getType() {
        return type;
    }
    public CardColor getColor() {
        return color;
    }
    public String getNumber() {
        return number;
    }
    public short getCvv() {
        return cvv;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public boolean getIsActive(){ return isActive;}

}
