package com.example.clientuser.viewmodel.formviewmodel

import com.example.clientuser.model.Address
import kotlinx.coroutines.flow.MutableStateFlow

data class AddressState(
    val street: String = "",
    val city: String = "",
    val civicNumber: String = "",
    val zipCode: String = "",
    val state: String = "",
    val defaultAddress: Boolean = false,
    //Error
    val isStreetError: Boolean = Address.validateStreet(street),
    val isCityError: Boolean = Address.validateCity(city),
    val isCivicNumberError: Boolean = Address.validateCivicNumber(civicNumber),
    val isZipCodeError: Boolean = Address.validateZipCode(zipCode),
    val isStateError: Boolean = Address.validateState(state),
)

class AddressFormViewModel {
    private val _addressState = MutableStateFlow(AddressState())
    val addressState = _addressState

    fun updateStreet(street: String) {
        _addressState.value = _addressState.value.copy(
            street = street,
            isStreetError = Address.validateStreet(street)
        )
    }
    fun updateCity(city: String) {
        _addressState.value = _addressState.value.copy(
            city = city,
            isCityError = Address.validateCity(city)
        )
    }
    fun updateCivicNumber(civicNumber: String) {
        _addressState.value = _addressState.value.copy(
            civicNumber = civicNumber,
            isCivicNumberError = Address.validateCivicNumber(civicNumber)
        )
    }
    fun updateZipCode(zipCode: String) {
        _addressState.value = _addressState.value.copy(
            zipCode = zipCode,
            isZipCodeError = Address.validateZipCode(zipCode)
        )
    }
    fun updateState(state: String) {
        _addressState.value = _addressState.value.copy(
            state = state,
            isStateError = Address.validateState(state)
        )
    }
    fun updateDefaultAddress(defaultAddress: Boolean) {
        _addressState.value = _addressState.value.copy(defaultAddress = defaultAddress)
    }
}