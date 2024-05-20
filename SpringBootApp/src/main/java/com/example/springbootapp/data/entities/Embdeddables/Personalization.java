package com.example.springbootapp.data.entities.Embdeddables;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Personalization {

    private static final Double PLAYER_NAME_PRICE = 10.0;
    private static final Double PLAYER_NUMBER_PRICE = 5.0;
    private String PlayerName;
    private Integer PlayerNumber;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personalization that = (Personalization) o;
        return PlayerName.equals(that.PlayerName) &&
                PlayerNumber.equals(that.PlayerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PlayerName, PlayerNumber);
    }
}
