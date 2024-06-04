package com.example.clientuser.model

import com.example.clientuser.model.dto.AddressDto

class Address(
    val id: String,
    val street: String,
    val city: String,
    val civicNumber: String,
    val zipCode: String,
    val state: String,
    val defaultAddress: Boolean
) {
    init {
        require(validateStreet(street)) { "Invalid street" }
        require(validateCity(city)) { "Invalid city" }
        require(validateCivicNumber(civicNumber)) { "Invalid civic number" }
        require(validateZipCode(zipCode)) { "Invalid zip code" }
        require(validateState(state)) { "Invalid state" }
    }

    companion object{
        fun validateStreet(street: String): Boolean {
            return street.isNotEmpty()
        }
        fun validateCity(city: String): Boolean {
            return city.isNotEmpty()
        }
        fun validateCivicNumber(civicNumber: String): Boolean {
            return civicNumber.isNotEmpty()
        }
        fun validateZipCode(zipCode: String): Boolean {
            return zipCode.isNotEmpty()
        }
        fun validateState(state: String): Boolean {
            return state.isNotEmpty()
        }

        fun fromDto(addressDto: AddressDto): Address{
            return Address(
                id = addressDto.id,
                street = addressDto.street,
                city = addressDto.city,
                civicNumber = addressDto.civicNumber,
                zipCode = addressDto.zipCode,
                state = addressDto.state,
                defaultAddress = addressDto.defaultAddress
            )
        }
    }
}