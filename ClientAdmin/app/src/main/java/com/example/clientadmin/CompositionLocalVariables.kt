package com.example.clientadmin

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.example.clientadmin.model.Admin
import com.example.clientadmin.viewmodels.*

val LocalCustomersViewModel = compositionLocalOf<CustomersViewModel> { error("No CustomerViewModel provided") }
val LocalProductsViewModel = compositionLocalOf<ProductsViewModel> { error("No ProductsViewModel provided") }
val LocalClubViewModel = compositionLocalOf<ClubViewModel> { error("No ClubViewModel provided") }
val LocalLeagueViewModel = compositionLocalOf<LeagueViewModel> { error("No LeagueViewModel provided") }
val LocalHomeViewModel = compositionLocalOf<HomeViewModel> { error("No HomeViewModel provided") }
val LocalCustomerViewModel = compositionLocalOf<CustomerViewModel> { error("No CustomerViewModel provided") }
val LocalProductViewModel = compositionLocalOf<ProductViewModel> { error("No ProductViewModel provided") }