package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.CartItem
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.CartItemDto
import com.example.clientuser.model.dto.UpdateCartItemDto

import com.example.clientuser.service.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CartViewModel : ViewModel() {
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart = _cart

    init{
        getMyCart()
    }

    fun addProductToCart(addToCartRequestDto: AddToCartRequestDto){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.cartApi.addProductToCart(addToCartRequestDto).awaitResponse()

                if(response.isSuccessful){
                    //TODO va bene o conviene fare _cart MutableList?
                    _cart.value += CartItem.fromDto(response.body()!!)
                }
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
                if(response.isSuccessful) response.body()?.let {  result ->
                    _cart.value = result.map { cartItemDto -> CartItem.fromDto(cartItemDto) }
                }
                else println("Error getting cart: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception getting cart: ${e.message}")
            }
        }
    }

    fun updateItemCartQuantity(updateCartItemDto: UpdateCartItemDto){
        if(updateCartItemDto.quantity > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                for (item: CartItem in _cart.value)
                    if (item.id == updateCartItemDto.id) {
                        val response = RetrofitHandler.cartApi.updateItemCartQuantity(updateCartItemDto).awaitResponse()
                        if (response.isSuccessful)
                            _cart.value = _cart.value.map { cartItem ->
                                if (cartItem.id == updateCartItemDto.id) cartItem.copy(quantity = updateCartItemDto.quantity)
                                else cartItem
                            }
                        break
                    }
            }
        }
    }
}