package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.CartItemDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CartViewModel : ViewModel() {
    private val _cart = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cart = _cart

    init{
        getMyCart()
    }

    fun addProductToCart(addToCartRequestDto: AddToCartRequestDto){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.cartApi.addProductToCart(addToCartRequestDto).awaitResponse()

                if(response.isSuccessful) println(response.body())
                else println("Error during adding product to cart")
            }
        }
        catch (e : Exception){
            println("Exception in adding product to cart")
        }
    }

    //todo
    private fun getMyCart(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.cartApi.getMyCart().awaitResponse()
                if(response.isSuccessful) response.body()?.let { result -> _cart.value = result }
                else println("Error getting cart: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception getting cart: ${e.message}")
            }
        }
    }
}