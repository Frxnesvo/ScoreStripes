package com.example.clientuser.service.interfaces

import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.model.dto.WishlistItemDto
import retrofit2.Call
import retrofit2.http.GET

interface WishListApiService {
    @GET("/api/v1/wishlists/my-wishlist")
    fun getMyWishList() : Call<List<WishlistItemDto>>

    @GET("/api/v1/wishlists/public")
    fun getPublicWishlists() : Call<List<WishListDto>>

    @GET("api/v1/wishlists/shared-with-me")
    fun getSharedWithMeWishlists() : Call<List<WishListDto>>
}