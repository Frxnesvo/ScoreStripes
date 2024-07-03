package com.example.clientuser

import androidx.compose.runtime.compositionLocalOf
import com.example.clientuser.model.Customer
import com.example.clientuser.viewmodel.*

val LocalLoginViewModel = compositionLocalOf<LoginViewModel> { error("No LoginViewModel provided") }

val LocalCustomerViewModel = compositionLocalOf<CustomerViewModel> { error("No CustomerViewModel provided") }
val LocalCartViewModel = compositionLocalOf<CartViewModel> { error("No CartViewModel provided") }
val LocalWishListViewModel = compositionLocalOf<WishListViewModel> { error("No WishListViewModel provided") }
val LocalProductsViewModel = compositionLocalOf<ProductsViewModel> { error("No ProductsViewModel provided") }
val LocalProductViewModel = compositionLocalOf<ProductViewModel> { error("No ProductViewModel provided") }
val LocalClubViewModel = compositionLocalOf<ClubViewModel> { error("No ClubViewModel provided") }
val LocalLeagueViewModel = compositionLocalOf<LeagueViewModel> { error("No LeagueViewModel provided") }

val LocalCustomer = compositionLocalOf<Customer?> { null }
