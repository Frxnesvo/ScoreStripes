package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.AddToWishListRequestDto
import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.model.dto.WishlistItemDto
import com.example.clientuser.model.dto.WishlistShareTokenDto
import com.example.clientuser.model.dto.WishlistVisibilityDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface WishListApiService {
    @GET("/api/v1/wishlists/my-wishlist")
    fun getMyWishList() : Call<List<WishlistItemDto>>

    @GET("/api/v1/wishlists/public")
    fun getPublicWishlists() : Call<List<WishListDto>>

    @GET("/api/v1/wishlists/shared-with-me")
    fun getSharedWithMeWishlists() : Call<List<WishListDto>>

    @POST("/api/v1/wishlists/add-Item")
    fun addItemToWishlist(@Body addToWishListItemDto: AddToWishListRequestDto) : Call<String>

    @POST("/api/v1/wishlists/create-shareToken")
    fun createShareToken() : Call<WishlistShareTokenDto>

    @PATCH("/api/v1/wishlists/change-visibility")
    fun changeWishlistVisibility(@Body wishlistVisibilityDto: WishlistVisibilityDto) : Call<String>
}