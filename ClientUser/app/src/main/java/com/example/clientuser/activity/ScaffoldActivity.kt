package com.example.clientuser.activity

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientuser.LocalCustomer
import com.example.clientuser.LocalWishListViewModel
import com.example.clientuser.R
import com.example.clientuser.authentication.LogoutManager
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel

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
                .fillMaxSize()
                .background(colorResource(id = R.color.primary))
                .padding(
                    top = it.calculateTopPadding() + 10.dp,
                    bottom = it.calculateBottomPadding(),
                    start = 10.dp,
                    end = 10.dp
                )

        ) {
            AuthAwareComposable {
                NavigationScaffold(
                    navHostController = navController,
                )
            }
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
            size = 40.dp,
            background = colorResource(id = if (Screen.HOME == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.HOME == selectedScreen.value) R.color.white else R.color.black50),
            content = "SS"
        ) {
            selectedScreen.value = Screen.HOME
            navHostController.navigate("home")
        }

        BoxIcon(
            size = 40.dp,
            background = colorResource(id = if (Screen.WISHLIST == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.WISHLIST == selectedScreen.value) R.color.white else R.color.black50),
            content = Icons.Outlined.FavoriteBorder
        ) {
            selectedScreen.value = Screen.WISHLIST
            navHostController.navigate("wishlist")
        }

        BoxIcon(
            size = 40.dp,
            background = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.white else R.color.black50),
            content = Icons.Outlined.ShoppingCart
        ) {
            selectedScreen.value = Screen.CART
            navHostController.navigate("cart")
        }

        BoxIcon(
            size = 40.dp,
            background = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.white else R.color.black50),
            content = LocalCustomer.current?.pic ?: Icons.Outlined.Person
        ) {
            selectedScreen.value = Screen.SETTINGS
            navHostController.navigate("settings")
        }
    }
}

@Composable
fun NavigationScaffold(
    navHostController: NavHostController,
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
                navHostController = navHostController
            )
        }

        //WISHLIST
        composable(
            route = "wishlist"
        ) {
            Wishlist(
                navHostController = navHostController
            )
        }

        composable(
            route = "discoverWishlist",
        ) {
            WishlistDiscover(
                navHostController = navHostController
            )
        }

        composable(
            route = "myWishlist",
        ) {
            WishlistProducts(
                wishlist = LocalWishListViewModel.current.myWishList.collectAsState().value,
                navHostController = navHostController
            )
        }

        composable(
            route = "sharedWishlistProducts/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->

                LocalWishListViewModel.current
                    .sharedWithMeWishlists.collectAsState().value
                    .find { product -> product.id == id }?.let {
                        wishlist ->
                        WishlistProducts(
                            wishlist = wishlist,
                            navHostController = navHostController
                        )
                    }?: ToastManager.show("Wishlist not found")
            }
        }

        composable(
            route = "publicWishlistProducts/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->

                LocalWishListViewModel.current
                    .publicWishLists.collectAsState(initial = emptyList()).value
                    .find { product -> product.id == id }?.let {
                        wishlist ->
                        WishlistProducts(
                            wishlist = wishlist,
                            navHostController = navHostController
                        )
                    }?: ToastManager.show("Wishlist not found")
            }
        }

        //CART
        composable(
            route = "cart"
        ) {
            Cart(
                navHostController = navHostController
            )
        }

        //SETTINGS
        composable(
            route = "settings"
        ) {
            LocalCustomer.current?.let { customer ->
                CustomerProfile(customer = customer, navHostController = navHostController)
            } ?: ToastManager.show("Customer not found")
        }
        composable(
            route = "details"
        ) {
            LocalCustomer.current?.let { customer ->
                CustomerDetails(
                    customer = customer,
                    navHostController = navHostController
                )
            } ?: ToastManager.show("Customer not found")
        }
        composable(
            route = "orders",
        ) {
            Orders(navHostController = navHostController)
        }
        composable(
            route = "addresses",
        ) {
            Addresses(navHostController = navHostController)
        }

        //LISTS
        composable(
            route = "list",
        ) {
            ListDiscover(
                name = stringResource(id = R.string.discover),
                navHostController = navHostController
            )
        }

        composable(
            route= "product/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ){ it ->
            it.arguments?.getString("id")?.let { id ->
                ProductDetails(
                    productId = id,
                    navHostController = navHostController,
                    productFormViewModel = ProductFormViewModel(),
                )
            }
        }

        composable(
            route = "list/club/{name}",
            arguments = listOf(navArgument("name"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("name")?.let {
                club ->
                ListDiscover(
                    name = club,
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
                ListDiscover(
                    name = league,
                    navHostController = navHostController,
                )
            }
        }

        //PAYMENT
        composable(
            route = "payment_success",
        ) {
            PaymentSuccessScreen(navHostController = navHostController)
        }
        composable(
            route = "payment_failure"
        ) {
            PaymentFailureScreen(navHostController = navHostController)
        }
    }
}

@Composable
fun AuthAwareComposable(
    content: @Composable () -> Unit
){
    val logoutManager = remember { LogoutManager.instance }
    val context = LocalContext.current

    LaunchedEffect(logoutManager) {
        logoutManager.logoutEvent.collect {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    content()
}
