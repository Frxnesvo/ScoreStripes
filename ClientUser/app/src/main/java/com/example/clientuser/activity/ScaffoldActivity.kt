package com.example.clientuser.activity

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
import com.example.clientuser.authentication.LogoutManager
import com.example.clientuser.model.Customer
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.ClubViewModel
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.OrderViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.ProductsViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.CustomerFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel

enum class Screen{ HOME, WISHLIST, CART, SETTINGS }

@Composable
fun Scaffold(loginViewModel: LoginViewModel, navHostController: NavHostController) {
    val selectedScreen = remember { mutableStateOf(Screen.HOME) }
    val navController = rememberNavController()
    val customer = loginViewModel.user.collectAsState()

    Scaffold(
        bottomBar = { BottomBar(navController, selectedScreen, customer.value) }
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
            AuthAwareComposable(navController = navHostController) {
                NavigationScaffold(
                    customerFormViewModel = CustomerFormViewModel(if(customer.value != null) customer.value!! else Customer()),
                    customerViewModel = CustomerViewModel(if(customer.value != null) customer.value!!.id else ""),
                    clubViewModel = ClubViewModel(),
                    leagueViewModel = LeagueViewModel(),
                    productsViewModel = ProductsViewModel(),
                    orderViewModel = OrderViewModel(),
                    wishlistViewModel = WishListViewModel(),
                    cartViewModel = CartViewModel(),
                    navHostController = navController,
                    productViewModel = ProductViewModel()
                )
            }
        }
    }
}

@Composable
fun BottomBar(navHostController: NavHostController, selectedScreen: MutableState<Screen>, customer: Customer?) {
    val showSnackBar = remember { mutableStateOf(false) }

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
            if(LogoutManager.instance.isLoggedIn.value) {
                selectedScreen.value = Screen.WISHLIST
                navHostController.navigate("wishlist")
            }else showSnackBar.value = true
        }

        BoxIcon(
            size = 40.dp,
            background = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.CART == selectedScreen.value) R.color.white else R.color.black50),
            content = Icons.Outlined.ShoppingCart
        ) {
            if(LogoutManager.instance.isLoggedIn.value){
                selectedScreen.value = Screen.CART
                navHostController.navigate("cart")
            } else showSnackBar.value = true

        }

        BoxIcon(
            size = 40.dp,
            background = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.secondary else R.color.transparent),
            iconColor = colorResource(id = if (Screen.SETTINGS == selectedScreen.value) R.color.white else R.color.black50),
            content = customer?.pic ?: Icons.Outlined.Person
        ) {
            if(LogoutManager.instance.isLoggedIn.value){
                selectedScreen.value = Screen.SETTINGS
                navHostController.navigate("settings")
            } else showSnackBar.value = true
        }
    }
    
    if(showSnackBar.value){
        GoToLoginSnackBar(navController = navHostController, onDismiss = {showSnackBar.value = false})
    }
}

@Composable
fun NavigationScaffold(
    navHostController: NavHostController,
    customerViewModel: CustomerViewModel,
    leagueViewModel: LeagueViewModel,
    clubViewModel: ClubViewModel,
    productsViewModel: ProductsViewModel,
    orderViewModel: OrderViewModel,
    wishlistViewModel: WishListViewModel,
    cartViewModel: CartViewModel,
    customerFormViewModel: CustomerFormViewModel,
    productViewModel: ProductViewModel,
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
                customerFormViewModel = customerFormViewModel,
                leagueViewModel = leagueViewModel,
                productsViewModel = productsViewModel,
                navHostController = navHostController,
                productViewModel = productViewModel
            )
        }

        //WISHLIST
        composable(
            route = "wishlist"
        ) {
            Wishlist(
                wishListViewModel = wishlistViewModel,
                navHostController = navHostController
            )
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
            val myList by wishlistViewModel.myWishList
            WishlistProducts(
                name = stringResource(id = R.string.my_wishlist),
                items = myList,
                navHostController = navHostController,
                productViewModel = productViewModel
            )
        }

        composable(
            route = "sharedWishlistProducts/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("id")?.let { id ->
                val wishlists by wishlistViewModel.sharedWithMeWishlists
                val wishlist = wishlists.find { it.id == id }
                WishlistProducts(
                    name = wishlist?.ownerUsername ?: "",
                    items = wishlist!!, //wishlist?.items ?: emptyList(), TODO
                    navHostController = navHostController,
                    productViewModel = productViewModel
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
                    items = wishlist!!, //TODO come sopra?
                    navHostController = navHostController,
                    productViewModel = productViewModel
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
            CustomerProfile(customerFormViewModel = customerFormViewModel, navHostController = navHostController)
        }
        composable(
            route = "details"
        ) {
            UserDetails(
                customerFormViewModel = customerFormViewModel,
                customerViewModel = customerViewModel,
                navHostController = navHostController,
                clubViewModel = clubViewModel
            )
        }
        composable(
            route = "orders",
        ) {
            Orders(
                customerViewModel = customerViewModel,
                navHostController = navHostController,
                productViewModel = productViewModel
            )
        }
        composable(
            route = "addresses",
        ) {
            Addresses(
                customerViewModel = customerViewModel,
                navHostController = navHostController
            )
        }

        //LISTS
        composable(
            route = "list",
        ) {
            ListDiscover(
                name = stringResource(id = R.string.discover),
                productsViewModel = productsViewModel,
                leagueViewModel = leagueViewModel,
                navHostController = navHostController,
                productViewModel = productViewModel
            )
        }


        //TODO cambiare senza usare launched effect
        composable(
            route= "product/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ){ it ->
            it.arguments?.getString("id")?.let { id ->
                ProductDetails(
                    productId = id,
                    wishListViewModel = wishlistViewModel,
                    navHostController = navHostController,
                    productFormViewModel = ProductFormViewModel(),
                    cartViewModel = cartViewModel,
                    productViewModel = productViewModel
                )
            }
        }


        composable(
            route = "list/club/{name}",
            arguments = listOf(navArgument("name"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("name")?.let {
                club ->
                productsViewModel.setFilter(mapOf("club" to club))
                ListDiscover(
                    name = club,
                    productsViewModel = productsViewModel,
                    leagueViewModel = leagueViewModel,
                    navHostController = navHostController,
                    productViewModel = productViewModel
                )
            }
        }

        composable(
            route = "list/league/{name}",
            arguments = listOf(navArgument("name"){ type = NavType.StringType })
        ) {
            it.arguments?.getString("name")?.let {
                league ->
                productsViewModel.setFilter(mapOf("league" to league))
                ListDiscover(
                    name = league,
                    productsViewModel = productsViewModel,
                    leagueViewModel = leagueViewModel,
                    navHostController = navHostController,
                    productViewModel = productViewModel
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
    navController: NavHostController,
    content: @Composable () -> Unit
){
    val logoutManager = remember { LogoutManager.instance }

    LaunchedEffect(logoutManager) {
        logoutManager.logoutEvent.collect {
            navController.navigate("index") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    content()
}
