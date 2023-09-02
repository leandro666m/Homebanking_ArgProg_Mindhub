package com.ap.homebanking.utils;
import java.util.Random;

public final class CardUtil {

    public static String CardNumberGenerator() {


    String cardNumber = "";
    for (int i=0; i<4 ;i++) {
            int random = new Random().nextInt(9999 - 1) + 1;
        if (i ==0 ){
            cardNumber = String.valueOf(random);
        }else {
            cardNumber = cardNumber + ("-" + String.valueOf(random) );
        }

    }

        return cardNumber;
    }
}
