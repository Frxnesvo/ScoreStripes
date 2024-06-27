package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.CartItem
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.UpdateCartItemDto

import com.example.clientuser.utils.RetrofitHandler
import com.example.clientuser.utils.ToastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CartViewModel : ViewModel() {
    private val _cart = MutableStateFlow<MutableMap<String, CartItem>>(mutableMapOf())
    val cart = _cart

    init{
        getMyCart()
    }

    fun addProductToCart(addToCartRequestDto: AddToCartRequestDto){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.cartApi.addProductToCart(addToCartRequestDto).awaitResponse()

                if(response.isSuccessful){
                    val cartItem = CartItem.fromDto(response.body()!!)
                    _cart.value[cartItem.id] = cartItem
                    ToastManager.show("product added to cart")
                }
                else println("Error during adding product to cart")
            }
        }
        catch (e : Exception){
            println("Exception in adding product to cart")
        }
    }

    private fun getMyCart(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.cartApi.getMyCart().awaitResponse()
                if(response.isSuccessful) response.body()?.let {  result ->
                    _cart.value = result.associateBy(
                        keySelector = { cartItemDto -> cartItemDto.id},
                        valueTransform = { cartItemDto -> CartItem.fromDto(cartItemDto)  }
                    ).toMutableMap()
                }
                else println("Error getting cart: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception getting cart: ${e.message}")
            }
        }
    }

    fun updateItemCartQuantity(updateCartItemDto: UpdateCartItemDto, itemId: String){
        if(updateCartItemDto.quantity in 1..99) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response =
                        RetrofitHandler.cartApi.updateItemCartQuantity(itemId, updateCartItemDto)
                            .awaitResponse()
                    if (response.isSuccessful)
                        _cart.value[itemId] = _cart.value[itemId]!!.copy(
                            quantity = updateCartItemDto.quantity
                        )
                    else println("Error updating cart item quantity: ${response.message()}")
                }
                catch (e : Exception){
                    println("Exception updating cart item quantity: ${e.message}")
                }
            }
        }
    }

    fun deleteItem(itemId: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitHandler.cartApi.deleteItemFromCart(itemId).awaitResponse()
                if(response.isSuccessful)
                    _cart.value.remove(itemId)
                else println("Error deleting the cart item: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception deleting the cart item: ${e.message}")
            }

        }
    }

    fun clearCart(){
        _cart.value = mutableMapOf()
    }
}