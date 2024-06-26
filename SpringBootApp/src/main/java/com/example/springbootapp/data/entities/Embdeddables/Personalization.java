package com.example.springbootapp.data.entities.Embdeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
public class Personalization {

    private static final Double PLAYER_NAME_PRICE = 10.0;
    private static final Double PLAYER_NUMBER_PRICE = 5.0;
    private String playerName;
    private Integer playerNumber;

    public double getPrice() {
        double price = 0.0;
        if (playerName != null && !playerName.isEmpty()) {
            price += PLAYER_NAME_PRICE;
        }
        if (playerNumber != null) {
            price += PLAYER_NUMBER_PRICE;
        }
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personalization that = (Personalization) o;
        return playerName.equals(that.playerName) &&
                playerNumber.equals(that.playerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, playerNumber);
    }
}
