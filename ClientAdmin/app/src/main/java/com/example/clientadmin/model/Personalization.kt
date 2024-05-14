package com.example.clientadmin.model

class Personalization(
    val playerName: String,
    val playerNumber: Int
) {
    companion object{
        val PLAYER_NAME_PRICE: Double = 10.0
        val PLAYER_NUMBER_PRICE: Double = 5.0
    }


    //modificate le condizioni, da verificare se vanno bene
    fun getPrice(): Double{
        var price = 0.0
        if (playerName != "") {
            price += PLAYER_NAME_PRICE
        }
        if (playerNumber > 0) {
            price += PLAYER_NUMBER_PRICE
        }
        return price
    }
}