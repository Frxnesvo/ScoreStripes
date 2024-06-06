package com.example.clientuser.viewmodel

import androidx.lifecycle.ViewModel
import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.model.dto.WishlistItemDto
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
    private val _shareWithMeWishlists = getSharedWithMeWishlists()
    val sharedWithMeWishlists = _shareWithMeWishlists

    private val _publicWishLists = getPublicWishlist()
    val publicWishLists = _publicWishLists

    private val _myWishList = MutableStateFlow<List<WishlistItemDto>>(emptyList())
    val myWishList = _myWishList


    private fun getMyWishList() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.getMyWishList().awaitResponse()
                if(response.isSuccessful) response.body()?.let { result -> _myWishList.value = result }
                else println("Error during the of the personal wishlists: ${response.message()}")
            }
            catch (e : Exception){
                println("Error during the get of the shared with me wishlists: ${e.message}")
            }
        }

    }

    private fun getSharedWithMeWishlists() : Flow<List<WishListDto>> = flow {
        try {
            val response = RetrofitHandler.wishListApi.getSharedWithMeWishlists().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error during the get of the shared with me wishlists: ${response.message()}")
        }
        catch (e : Exception) {
            println("Exception during the get of the shared with me wishlists: ${e.message}")
        }

    }

    fun getPublicWishlist() : Flow<List<WishListDto>> = flow {
        try{
            val response = RetrofitHandler.wishListApi.getPublicWishlists().awaitResponse()
            if(response.isSuccessful) response.body()?.let { emit(it) }
            else println("Error during the get of the public wishlists: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception during the get of the public wishlists: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}