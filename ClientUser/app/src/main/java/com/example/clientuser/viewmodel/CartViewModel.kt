package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CartViewModel : ViewModel() {

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
}