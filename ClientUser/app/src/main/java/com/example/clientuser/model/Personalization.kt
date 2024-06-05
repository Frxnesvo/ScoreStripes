package com.example.clientuser.model

class Personalization(
    val playerName: String?,
    val playerNumber: Int?
) {
    companion object{
        const val PLAYER_NAME_PRICE: Double = 10.0
        const val PLAYER_NUMBER_PRICE: Double = 5.0
    }


    fun getPrice(): Double{
        var price = 0.0
        if (playerName != null) {
            price += PLAYER_NAME_PRICE
        }
        if (playerNumber != null) {
            price += PLAYER_NUMBER_PRICE
        }
        return price
    }
}