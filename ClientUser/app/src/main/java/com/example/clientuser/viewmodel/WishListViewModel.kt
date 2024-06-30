package com.example.clientuser.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clientuser.model.CustomerSummary
import com.example.clientuser.model.Wishlist
import com.example.clientuser.model.WishlistItem
import com.example.clientuser.model.dto.AddToWishListRequestDto
import com.example.clientuser.model.dto.WishlistVisibilityDto
import com.example.clientuser.model.enumerator.WishListVisibility
import com.example.clientuser.utils.RetrofitHandler
import com.example.clientuser.utils.ToastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WishListViewModel : ViewModel() {
    //TODO va bene o devo usare MutableStateFlow?
    private val _sharedWithMeWishlists = mutableStateOf<List<Wishlist>>(emptyList())
    val sharedWithMeWishlists = _sharedWithMeWishlists

    private val _publicWishLists = fetchPublicWishlist()
    val publicWishLists = _publicWishLists

    private val _myWishList = mutableStateOf(Wishlist())
    val myWishList = _myWishList

    private val _wishlistSharedToken = mutableStateOf("")
    val wishlistSharedToken: State<String> = _wishlistSharedToken

    private val _myWishlistAccesses = MutableStateFlow<List<CustomerSummary>>(emptyList())
    val myWishlistAccesses = _myWishlistAccesses.asStateFlow()

    init{
        fetchMyWishList()
        fetchMyWishlistAccesses()
        fetchSharedWithMeWishlists()
    }

    private fun fetchMyWishList() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.getMyWishList().awaitResponse()
                if(response.isSuccessful) response.body()?.let { result ->
                    _myWishList.value = Wishlist.fromDto(result)
                }
                else println("Error during the of the personal wishlists: ${response.message()}")
            }
            catch (e : Exception){
                println("Error during the get of the shared with me wishlists: ${e.message}")
            }
        }

    }

    private fun fetchSharedWithMeWishlists(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.wishListApi.getSharedWithMeWishlists().awaitResponse()
                if(response.isSuccessful) response.body()?.let {
                    list -> _sharedWithMeWishlists.value = (list.map { Wishlist.fromDto(it) })
                }
                else println("Error during the get of the shared with me wishlists: ${response.message()}")
            }

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
                    val newItems = _myWishList.value.items + WishlistItem.fromDto(it)

                    _myWishList.value = Wishlist(
                        id = _myWishList.value.id,
                        ownerUsername = _myWishList.value.ownerUsername,
                        visibility = _myWishList.value.visibility,
                        items = newItems
                    )

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
                if(response.isSuccessful) response.body()?.let {
                    _wishlistSharedToken.value = it.token
                    println("SHARED TOKEN VIEWMODEl: ${wishlistSharedToken.value}")
                }
                else Log.e("errore wishlist","Error during the creation of the wishlist shared token: ${response.message()}")
            }
            catch (e : Exception){
                Log.e("errore wishlist","Exception during the creation of the wishlist shared token: ${e.message}")
            }
        }
    }

    fun changeWishlistVisibility(wishlistVisibilityDto: WishlistVisibilityDto){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = RetrofitHandler.wishListApi.changeWishlistVisibility(wishlistVisibilityDto).awaitResponse()
                if(response.isSuccessful) {
                    response.body()?.let {
                        _myWishList.value = _myWishList.value.copy(
                            visibility = wishlistVisibilityDto.visibility
                        )
                        if (wishlistVisibilityDto.visibility == WishListVisibility.PRIVATE) _myWishlistAccesses.value =
                            emptyList()
                        ToastManager.show("wishlist visibility changed from ${_myWishList.value.visibility.name} to ${wishlistVisibilityDto.visibility.name}")
                    }
                }
                else {
                    println("Error during the visibility changing of the wishlist: ${response.message()}")
                    ToastManager.show("Error during the change of the wishlist visibility")
                }
            }
            catch (e : Exception){
                println("Exception during the visibility changing of the wishlist: ${e.message}")
            }
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

    fun deleteWishlistAccess(guestId: String){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.wishListApi.deleteWishlistAccess(guestId).awaitResponse()
                if(response.isSuccessful){
                    val guestToDelete = _myWishlistAccesses.value.find { it.id == guestId }
                    _myWishlistAccesses.value -= guestToDelete!!
                    ToastManager.show("Access deleted from wishlist")
                } else {
                    println("Error delete wishlist access: ${response.message()}")
                    ToastManager.show("Error delete wishlist access")
                }
            }
        }
        catch (e : Exception){
            println("Exception delete wishlist access: ${e.message}")
        }
    }

    fun deleteItem(productId: String): Flow<String> = flow {
        try{
            val response = RetrofitHandler.wishListApi.deleteItem(productId).awaitResponse()
            if(response.isSuccessful) response.body()?.let { result ->
                val itemToDelete = _myWishList.value.items.find { it.product.id == productId }!!

                val newItems = _myWishList.value.items - itemToDelete

                _myWishList.value = Wishlist(
                    id = _myWishList.value.id,
                    ownerUsername = _myWishList.value.ownerUsername,
                    visibility = _myWishList.value.visibility,
                    items = newItems
                )

                emit(result)
            }
            else println("Error delete wishlist item: ${response.message()}")
        }
        catch (e : Exception){
            println("Exception delete wishlist item: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)

    fun validateShareToken(token: String) {
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitHandler.wishListApi.validateShareToken(mapOf(Pair("message", token))).awaitResponse()
                if(response.isSuccessful){
                    response.body()?.let { wishListDto ->  
                        _sharedWithMeWishlists.value += Wishlist.fromDto(wishListDto)
                        ToastManager.show("wishlist added")
                    }
                }
                else {
                    println("Error validate wishlist share token: ${response.message()}")
                    ToastManager.show("Error adding wishlist")
                }
            }
        }catch (e: Exception){
            println("Exception validate wishlist share token: ${e.message}")
        }
    }

    fun clearWishlistShareToken(){
        _wishlistSharedToken.value = ""
    }
}