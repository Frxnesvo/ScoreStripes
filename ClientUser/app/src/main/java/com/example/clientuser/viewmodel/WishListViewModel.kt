package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.Wishlist
import com.example.clientuser.model.WishlistItem
import com.example.clientuser.model.dto.AddToWishListRequestDto
import com.example.clientuser.model.dto.WishlistShareTokenDto
import com.example.clientuser.model.dto.WishlistVisibilityDto
import com.example.clientuser.service.RetrofitHandler
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

    private val _myWishList = MutableStateFlow<MutableList<WishlistItem>>(mutableListOf())
    val myWishList = _myWishList

    init{
        fetchMyWishList()
    }

    private fun fetchMyWishList() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.getMyWishList().awaitResponse()
                if(response.isSuccessful) response.body()?.let { result ->
                    _myWishList.value = result.map { wishlistItemDto -> WishlistItem.fromDto(wishlistItemDto)  }.toMutableList()
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

    }

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
                    _myWishList.value.add(WishlistItem.fromDto(it))
                }
                else println("Error during the add of a product to the wishlists: ${response.message()}")
            }
            catch (e : Exception){
                println("Error during the add of a product to the wishlists: ${e.message}")
            }
        }
    }

    fun createShareToken() : Flow<WishlistShareTokenDto> = flow {
        try{
            val response = RetrofitHandler.wishListApi.createShareToken().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error during the creation of the wishlist shared token: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the creation of the wishlist shared token: ${e.message}")
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
}