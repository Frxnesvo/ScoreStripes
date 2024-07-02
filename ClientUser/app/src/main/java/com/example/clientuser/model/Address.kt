package com.example.clientuser.model

import java.io.Serializable

class Address(
    val street: String = "",
    val city: String = "",
    val civicNumber: String = "",
    val zipCode: String = "",
    val state: String = "",
    val defaultAddress: Boolean = false
): Serializable {
    init {
        require(validateStreet(street)) { "Invalid street" }
        require(validateCity(city)) { "Invalid city" }
        require(validateCivicNumber(civicNumber)) { "Invalid civic number" }
        require(validateZipCode(zipCode)) { "Invalid zip code" }
        require(validateState(state)) { "Invalid state" }
    }

    companion object{
        fun validateStreet(street: String): Boolean {
            return street.isNotBlank()
        }
        fun validateCity(city: String): Boolean {
            return city.isNotBlank()
        }
        fun validateCivicNumber(civicNumber: String): Boolean {
            return civicNumber.matches("\\d+[A-Za-z]?(/[A-Za-z]?)?" .toRegex())
        }
        fun validateZipCode(zipCode: String): Boolean {
            return zipCode.matches("\\d{5}" .toRegex())
        }
        fun validateState(state: String): Boolean {
            return state.isNotBlank()
        }
    }
}