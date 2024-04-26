package com.example.springbootapp.Data.Entities.Embdeddables;

import com.example.springbootapp.Data.Entities.Enums.Size;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Personalization {

    private static final Double PLAYER_NAME_PRICE = 10.0;
    private static final Double PLAYER_NUMBER_PRICE = 5.0;
    private String PlayerName;
    private Integer PlayerNumber;
    @Enumerated(EnumType.STRING)
    private Size size;

    public double getPrice() {
        double price = 0.0;
        if (PlayerName != null) {
            price += PLAYER_NAME_PRICE;
        }
        if (PlayerNumber != null) {
            price += PLAYER_NUMBER_PRICE;
        }
        return price;
    }
}
