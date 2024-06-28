package com.example.clientuser.viewmodel.formviewmodel

import com.example.clientuser.model.Personalization
import com.example.clientuser.model.PersonalizationPrice
import com.example.clientuser.model.enumerator.Size
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductState(
    val name: String = "",
    val number: Int = 0,
    val size: Size? = null,
)
class ProductFormViewModel {
    private val _productState = MutableStateFlow(ProductState())
    val productState = _productState.asStateFlow()

    fun updatePersonalizationName(name: String){
        _productState.value = _productState.value.copy(
            name = name
        )
    }

    fun updatePersonalizationNumber(number: Int){
        _productState.value = _productState.value.copy(
            number = number
        )
    }

    fun updateProductSize(size: Size?){
        _productState.value = _productState.value.copy(
            size = size
        )
    }

    fun calculatePersonalizationPrice() : Double{
        var personalizationPrice = 0.0
        if(_productState.value.name != "")personalizationPrice += PersonalizationPrice.PLAYER_NAME_PRICE
        if(_productState.value.number > 0)personalizationPrice += PersonalizationPrice.PLAYER_NUMBER_PRICE
        return personalizationPrice
    }

}