package com.example.clientuser.viewmodel.formviewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


//TODO usare customerFormViewModel al posto di LoginFormViewModel?
class CustomerFormViewModel {
    private val _customer = MutableStateFlow(CustomerState())
    val customer = _customer.asStateFlow()

    fun updateFavouriteTeam(favouriteTeam: String){
        _customer.value = _customer.value.copy(
            favouriteTeam = favouriteTeam
        )
    }
}