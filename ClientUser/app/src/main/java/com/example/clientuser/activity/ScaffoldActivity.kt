package com.example.clientuser.activity

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientuser.R
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.ClubViewModel
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.OrderViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.CustomerFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel

enum class Screen{ HOME, WISHLIST, CART, SETTINGS }

@Composable
fun Scaffold() {
    val selectedScreen = remember { mutableStateOf(Screen.HOME) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController, selectedScreen) }
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding() + 10.dp,
                    bottom = it.calculateBottomPadding(),
                    start = 10.dp,
                    end = 10.dp
                )
        ) {
            NavigationScaffold(
                customerFormViewModel = CustomerFormViewModel(), //TODO passare il customer state dal loginFormViewModel
                customerViewModel = CustomerViewModel(""), //TODO passare l'id dell'utente
                clubViewModel = ClubViewModel(),
                leagueViewModel = LeagueViewModel(),
                productViewModel = ProductViewModel(),
                orderViewModel = OrderViewModel(),
                wishlistViewModel = WishListViewModel(),
                cartViewModel = CartViewModel(),
                navHostController = navController
            )
        }
    }
}

@Composable
fun BottomBar(navHostController: NavHostController, selectedScreen: MutableState<Screen>) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                colorResource(id = R.color.white),
                RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    ) {
        BoxIcon(
            background = colorResource(id = if (Screen.HOME == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.HOME == selectedScreen.value) R.color.white else R.color.black50),
            content = "SS"
        ) {
            selectedScreen.value = Screen.HOME
            navHostController.navigate("home")
        }

        BoxIcon(
            background = colorResource(id = if (Screen.WISHLIST == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.WISHLIST == selectedScreen.value) R.color.white else R.color.black50),
            content = Icons.Outlined.FavoriteBorder
        ) {
            selectedScreen.value = Screen.WISHLIST
            navHostController.navigate("wishlist")
        }

        BoxIcon(
            background = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.white else R.color.black50),
            content = Icons.Outlined.ShoppingCart
        ) {
            selectedScreen.value = Screen.CART
            navHostController.navigate("cart")
        }

        BoxIcon(
            background = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.white else R.color.black50),
            content = if(true) Icons.Outlined.Person else Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) //todo verificare se si è loggati
        ) {
            selectedScreen.value = Screen.SETTINGS
            navHostController.navigate("settings")
        }
    }
}

@Composable
fun NavigationScaffold(
    navHostController: NavHostController,
    customerViewModel: CustomerViewModel,
    leagueViewModel: LeagueViewModel,
    clubViewModel: ClubViewModel,
    productViewModel: ProductViewModel,
    orderViewModel: OrderViewModel,
    wishlistViewModel: WishListViewModel,
    cartViewModel: CartViewModel,
    customerFormViewModel: CustomerFormViewModel,
) {
    NavHost(
        modifier = Modifier.background(colorResource(R.color.primary)),
        navController = navHostController,
        startDestination = "home"
    ) {
        //HOME
        composable(
            route = "home"
        ) {
            Home(
                leagueViewModel = leagueViewModel,
                clubViewModel = clubViewModel,
                productViewModel = productViewModel,
                navHostController = navHostController
            )
        }

        //WISHLIST
        composable(
            route = "wishlist"
        ) {
            Wishlist(
                wishListViewModel = wishlistViewModel,
                navHostController = navHostController)
        }

        composable(
            route = "discoverWishlist",
        ) {
            WishlistDiscover(
                wishListViewModel = wishlistViewModel,
                navHostController = navHostController
            )
        }

        composable(
            route = "myWishlist",
        ) {
            val myList by wishlistViewModel.myWishList.collectAsState()
            WishlistProducts(
                name = stringResource(id = R.string.my_wishlist),
                items = myList,
                navHostController = navHostController
            )
        }

        composable(
            route = "sharedWishlistProducts/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                val wishlists by wishlistViewModel.sharedWithMeWishlists.collectAsState(initial = emptyList())
                val wishlist = wishlists.find { it.id == id }
                WishlistProducts(
                    name = wishlist?.ownerUsername ?: "",
                    items = wishlist?.items ?: emptyList(),
                    navHostController = navHostController
                )
            }
        }

        composable(
            route = "publicWishlistProducts/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                val wishlists by wishlistViewModel.publicWishLists.collectAsState(initial = emptyList())
                val wishlist = wishlists.find { it.id == id }
                WishlistProducts(
                    name = wishlist?.ownerUsername ?: "", //faccio così per gli aggiornamenti in tempo reale, ma non è il massimo
                    items = wishlist?.items ?: emptyList(),
                    navHostController = navHostController
                )
            }
        }

        //CART
        composable(
            route = "cart"
        ) {
            Cart(
                customerViewModel = customerViewModel,
                orderViewModel = OrderViewModel(),
                cartViewModel = cartViewModel,
                navHostController = navHostController
            )
        }

        //SETTINGS
        composable(
            route = "settings"
        ) {
            CustomerProfile(navHostController = navHostController)
        }
        composable(
            route = "details"
        ) {
            UserDetails(
                customerFormViewModel = customerFormViewModel, //todo cambiare con customer form view model
                customerViewModel = customerViewModel, //todo cambiare con customer view model
                navHostController = navHostController,
                clubViewModel = clubViewModel
            )
        }
        composable(
            route = "orders",
        ) {
            Orders(
                customerViewModel = customerViewModel,
                navHostController = navHostController
            )
        }
        composable(
            route = "addresses",
        ) {
            Addresses(
                customerViewModel = customerViewModel,
                addressFormViewModel = AddressFormViewModel(),
                navHostController = navHostController
            )
        }

        //LISTS
        composable(
            route = "list",
        ) {
            ListDiscover(
                name = stringResource(id = R.string.discover),
                productViewModel = productViewModel,
                leagueViewModel = leagueViewModel,
                navHostController = navHostController
            )
        }
        composable(
            route = "list/club/{name}",
            arguments = listOf(navArgument("name"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("name")?.let {
                club ->
                productViewModel.setFilter(FilterBuilder().setClub(club).build())
                ListDiscover(
                    name = stringResource(id = R.string.discover),
                    productViewModel = productViewModel,
                    leagueViewModel = leagueViewModel,
                    navHostController = navHostController
                )
            }
        }
        composable(
            route = "list/league/{name}",
            arguments = listOf(navArgument("name"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("name")?.let {
                league ->
                productViewModel.setFilter(FilterBuilder().setLeague(league).build())
                ListDiscover(
                    name = stringResource(id = R.string.discover),
                    productViewModel = productViewModel,
                    leagueViewModel = leagueViewModel,
                    navHostController = navHostController
                )
            }
        }

        //PAYMENT
        composable(
            route = "splash_screen/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let {
                sessionId -> orderViewModel.validateTransaction(sessionId)
                SplashScreen(navHostController, orderViewModel = orderViewModel)
            }
        }
        composable(
            route = "payment/{result}",
            arguments = listOf(navArgument("result"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("result")?.let {
                result -> PaymentSuccessScreen(result = result, navHostController = navHostController)
            }
        }
        composable(
            route = "payment_failure"
        ) {
            PaymentFailureScreen(navHostController = navHostController)
        }
    }
}
