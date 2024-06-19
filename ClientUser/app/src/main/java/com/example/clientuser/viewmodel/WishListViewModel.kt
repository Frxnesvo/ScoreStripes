package com.example.clientuser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.CustomerSummary
import com.example.clientuser.model.Wishlist
import com.example.clientuser.model.WishlistItem
import com.example.clientuser.model.dto.AddToWishListRequestDto
import com.example.clientuser.model.dto.WishlistVisibilityDto
import com.example.clientuser.utils.RetrofitHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WishListViewModel : ViewModel() {
    private val _sharedWithMeWishlists = fetchSharedWithMeWishlists()
    val sharedWithMeWishlists = _sharedWithMeWishlists

    private val _publicWishLists = fetchPublicWishlist()
    val publicWishLists = _publicWishLists

    private val _myWishList = MutableStateFlow<List<WishlistItem>>(emptyList())
    val myWishList = _myWishList
    //TODO val myWishList = _myWishList.asStateFlow()

    private val _wishlistSharedToken = MutableStateFlow("")
    val wishlistSharedToken = _wishlistSharedToken

    private val _myWishlistAccesses = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val myWishlistAccesses = _myWishlistAccesses

    init{
        fetchMyWishList()
        fetchMyWishlistAccesses()
    }

    private fun fetchMyWishList() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.getMyWishList().awaitResponse()
                if(response.isSuccessful) response.body()?.let { result ->
                    _myWishList.value = result.map { wishlistItemDto -> WishlistItem.fromDto(wishlistItemDto) }
                }
                else println("Error during the of the personal wishlists: ${response.message()}")
            }
            catch (e : Exception){
                println("Error during the get of the shared with me wishlists: ${e.message}")
            }
        }

    }

    private fun fetchSharedWithMeWishlists() : Flow<List<Wishlist>> = flow {
        try {
            val response = RetrofitHandler.wishListApi.getSharedWithMeWishlists().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it.map { Wishlist.fromDto(it) }) }
            else println("Error during the get of the shared with me wishlists: ${response.message()}")
        }
        catch (e : Exception) {
            println("Exception during the get of the shared with me wishlists: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    //TODO fare la paginazione
    private fun fetchPublicWishlist() : Flow<List<Wishlist>> = flow {
        try{
            val response = RetrofitHandler.wishListApi.getPublicWishlists().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it.map { Wishlist.fromDto(it) }) }
            else println("Error during the get of the public wishlists: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the get of the public wishlists: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun addItemToWishlist(addToWishListRequestDto: AddToWishListRequestDto){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.addItemToWishlist(addToWishListRequestDto).awaitResponse()
                if(response.isSuccessful) response.body()?.let {
                    _myWishList.value += WishlistItem.fromDto(it)
                }
                else println("Error during the add of a product to the wishlists: ${response.message()}")
            }
            catch (e : Exception){
                println("Error during the add of a product to the wishlists: ${e.message}")
            }
        }
    }

    fun createSharedToken() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.createShareToken().awaitResponse()
                if(response.isSuccessful) response.body()?.let { _wishlistSharedToken.value = it.token }
                else Log.e("errore wishlist","Error during the creation of the wishlist shared token: ${response.message()}")
            }
            catch (e : Exception){
                Log.e("errore wishlist","Exception during the creation of the wishlist shared token: ${e.message}")
            }
        }
    }

    fun changeWishlistVisibility(wishlistVisibilityDto: WishlistVisibilityDto) : Flow<String> = flow {
        try{
            val response = RetrofitHandler.wishListApi.changeWishlistVisibility(wishlistVisibilityDto).awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error during the visibility changing of the wishlist: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the visibility changing of the wishlist: ${e.message}")
        }
    }

    private fun fetchMyWishlistAccesses(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.getMyWishlistAccesses().awaitResponse()
                if(response.isSuccessful)response.body()?.let { result ->
                    _myWishlistAccesses.value = result.map { customerSummaryDto -> CustomerSummary.fromDto(customerSummaryDto) }
                }
                else println("Error get wishlist accesses: ${response.message()}")
            }
            catch (e : Exception){
                println("Exception get wishlist accesses: ${e.message}")
            }
        }
    }

    fun deleteWishlistAccess(guestId: String): Flow<String> = flow<String> {
        try{
            val response = RetrofitHandler.wishListApi.deleteWishlistAccess(guestId).awaitResponse()
            if(response.isSuccessful) response.body()?.let { result ->
                val guestToDelete = _myWishlistAccesses.value.find { it.id == guestId }
                _myWishlistAccesses.value -= guestToDelete!!
                emit(result)
            }
        }
        catch (e : Exception){
            println("Exception delete wishlist access: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun deleteItem(productId: String): Flow<String> = flow {
        try{
            val response = RetrofitHandler.wishListApi.deleteItem(productId).awaitResponse()
            if(response.isSuccessful) response.body()?.let { result ->
                val itemToDelete = _myWishList.value.find { it.product.id == productId }
                _myWishList.value -= itemToDelete!!
                emit(result)
            }
            else println("Error delete wishlist item: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception delete wishlist item: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}