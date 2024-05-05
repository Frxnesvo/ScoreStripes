package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Enum.Size
import com.example.clientadmin.model.Enum.Type
import java.time.Year

class Quantity(
    private var quantityXS: Int = 0,
    private var quantityS: Int = 0,
    private var quantityM: Int = 0,
    private var quantityL: Int = 0,
    private var quantityXL: Int = 0
){
    fun quantities(): List<Pair<Size, Int>>{
        return listOf(
            Size.XS to quantityXS,
            Size.S to quantityS,
            Size.M to quantityM,
            Size.L to quantityL,
            Size.XL to quantityXL
        )
    }
}

data class Season(
    val yearStart: Year,
    val yearEnd: Year
)

class Product(
    var id: Int,
    var team: String,
    var league: String,
    var season: Season,
    var type: Type,
    var category: Category,
    var description: String,
    var image1: Bitmap? = null,
    var image2: Bitmap? = null,
    var price: Double,
    var preferred: Boolean = false,
    var quantities: Quantity
){
    init {
        /*TODO - gestire gli errori*/

        if (!validateNameTeam(team))
            throw IllegalArgumentException("Name cannot be empty and greater than 20 characters")
        if (!validatePrice(price))
            throw IllegalArgumentException("Price cannot be negative")
        if (!validateSeason(season))
            throw IllegalArgumentException("End season cannot be before start season")
        if (!validateLeague(league))
            throw IllegalArgumentException("league cannot be empty and greater than 15 characters")
        if (!validateDescription(description))
            throw IllegalArgumentException("Description cannot be empty and greater than 80 characters")
    }
    companion object {
        fun validateNameTeam(teamName: String): Boolean {
            return teamName.length in 1..20
        }
        fun validateSeason(season: Season?): Boolean {
            return season != null && !season.yearStart.isAfter(season.yearEnd)
        }
        fun validatePrice(price: Double?): Boolean{
            return price != null && price >= 0
        }
        fun validateDescription(description: String): Boolean{
            return description.length in 1..80
        }
        fun validateLeague(league: String): Boolean{
            return league.length in 1..15
        }
    }
}