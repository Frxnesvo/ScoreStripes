package com.example.clientuser.viewmodel.formviewmodel

import android.graphics.Bitmap
import com.example.clientuser.model.Customer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class CustomerFormViewModel(customer: Customer) {

    private val _customer = MutableStateFlow(CustomerState())
    val customer = _customer.asStateFlow()

    init {
        _customer.value = _customer.value.copy(
            firstName = customer.firstName,
            lastName = customer.lastName,
            email = customer.email,
            username = customer.username,
            birthdate = customer.birthDate,
            gender = customer.gender,
            profilePic = customer.getPic(),
            favouriteTeam = customer.getFavoriteTeam()
        )
    }

    fun updateFavouriteTeam(favouriteTeam: String){
        _customer.value = _customer.value.copy(
            favouriteTeam = favouriteTeam
        )
    }

    fun updateProfilePic(pic: Bitmap?){
        _customer.value = _customer.value.copy(
            profilePic = pic
        )
    }
}