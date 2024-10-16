package com.example.clientadmin.activity

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientadmin.LocalClubViewModel
import com.example.clientadmin.LocalCustomerViewModel
import com.example.clientadmin.LocalCustomersViewModel
import com.example.clientadmin.LocalLeagueViewModel
import com.example.clientadmin.LocalProductViewModel
import com.example.clientadmin.R
import com.example.clientadmin.authentication.LogoutManager
import com.example.clientadmin.model.Admin
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.League
import com.example.clientadmin.utils.ToastManager
import com.example.clientadmin.viewmodels.formViewModel.LeagueFormViewModel
import com.example.clientadmin.viewmodels.formViewModel.ClubFormViewModel
import com.example.clientadmin.viewmodels.CustomerViewModel
import com.example.clientadmin.viewmodels.ProductViewModel
import com.example.clientadmin.viewmodels.formViewModel.FilterFormViewModel
import com.example.clientadmin.viewmodels.formViewModel.ProductFormViewModel

enum class Screen{ HOME, USERS, PRODUCTS, SETTINGS }

@Composable
fun Scaffold(admin: Admin) {
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
                    end = 10.dp,
                )
        ){
            AuthAwareComposable {
                CompositionLocalProvider(
                    LocalCustomerViewModel provides CustomerViewModel(),
                    LocalProductViewModel provides ProductViewModel(),
                ) {
                    NavigationScaffold(
                        navHostController = navController,
                        selectedScreen = selectedScreen,
                        admin = admin
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(navHostController: NavHostController, selectedScreen: MutableState<Screen>){
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

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
        BottomBarButton(
            indexColor = if (Screen.HOME == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = null
        ) {
            selectedScreen.value = Screen.HOME
            navHostController.navigate("home")
        }
        BottomBarButton(
            indexColor = if (Screen.USERS == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = Icons.Outlined.Person
        ) {
            selectedScreen.value = Screen.USERS
            navHostController.navigate("users")
        }
        BottomBarButton(
            indexColor = R.color.white,
            background = R.color.secondary,
            imageVector = Icons.Outlined.Add
        ) {
            setBottomSheet(true)
        }
        BottomBarButton(
            indexColor = if (Screen.PRODUCTS == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = Icons.AutoMirrored.Outlined.List
        ) {
            selectedScreen.value = Screen.PRODUCTS
            navHostController.navigate("products")
        }
        BottomBarButton(
            indexColor = if (Screen.SETTINGS == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = Icons.Outlined.Settings
        ) {
            selectedScreen.value = Screen.SETTINGS
            navHostController.navigate("settings")
        }
        if (isOpenSheet)
            AddPanel(
                onDismissRequest = { setBottomSheet(false) },
                setBottomSheet = setBottomSheet,
                navHostController = navHostController
            )
    }
}

@Composable
fun BottomBarButton(indexColor: Int, background: Int? = null, imageVector: ImageVector?, function: () -> Unit){
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { function() }
            .background(
                if (background != null) colorResource(id = background) else Color.Transparent,
                RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        if (imageVector != null)
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = colorResource(id = indexColor)
            )
        else
            Text(
                text = "SS",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = indexColor)
            )
    }
}

@Composable
fun NavigationScaffold(
    navHostController: NavHostController,
    selectedScreen: MutableState<Screen>,
    admin: Admin
) {
    NavHost(
        modifier = Modifier.background(colorResource(R.color.primary)),
        navController = navHostController,
        startDestination = "home"
    ){
        //HOME
        composable(
            route = "home"
        ){
            Home(selectedScreen = selectedScreen, navHostController = navHostController)
        }

        //USERS
        composable(
            route = "users"
        ){
            Users(navHostController = navHostController, filterFormViewModel = FilterFormViewModel())
        }
        composable(
            route = "user/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ){
            it.arguments?.getString("id")?.let {
                id -> LocalCustomersViewModel.current.customerSummaries.collectAsState().value.find {
                    customer -> customer.id == id }?.let {
                    customerSummary -> CustomerProfile(
                        customerSummary = customerSummary,
                        navHostController = navHostController
                    )
                }
            }
        }
        composable(
            route = "userDetails",
        ){
            LocalCustomerViewModel.current.customerProfile.collectAsState().value?.let {
                customer -> CustomerDetails(
                    customer = customer,
                    navHostController = navHostController
                )
            }
        }

        //ORDERS
        composable(
            route = "userOrders",
        ){
            List(
                navHostController = navHostController,
                isAddresses = false
            )
        }

        //ADDRESSES
        composable(
            route = "userAddresses",
        ){
            List(
                navHostController = navHostController,
                isAddresses = true
            )
        }

        //ADD
        composable(
            route = "addProduct"
        ){
            if (LocalClubViewModel.current.clubs.collectAsState().value.isNotEmpty()) {
                ProductDetails(
                    navHostController = navHostController,
                    productFormViewModel = ProductFormViewModel()
                )
            } else ToastManager.show("You need to add a club first")
        }
        composable(
            route = "addLeague"
        ){
            LeagueDetails(
                leagueFormViewModel = LeagueFormViewModel(),
                navHostController = navHostController,
                isAdd = true
            )
        }
        composable(
            route = "addTeam"
        ){
            if (LocalLeagueViewModel.current.leagues.collectAsState().value.isNotEmpty()) {
                ClubDetails(
                    clubFormViewModel = ClubFormViewModel(),
                    navHostController = navHostController,
                    isAdd = true
                )
            } else ToastManager.show("You need to add a league first")
        }

        //PRODUCTS
        composable(
            route = "products"
        ){
            Products(
                navHostController = navHostController,
                filterFormViewModel = FilterFormViewModel()
            )
        }
        composable(
            route= "product/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.StringType })
        ){
            it.arguments?.getString("id")?.let {
                id-> ProductDetails(
                    productFormViewModel = ProductFormViewModel(LocalProductViewModel.current.product.collectAsState().value),
                    navHostController = navHostController,
                    id = id
                )
            }
        }

        composable(
            route = "league/{league}",
            arguments = listOf(navArgument("league"){ type = NavType.StringType })
        ){
            it.arguments?.getString("league")?.let {
                leagueString ->
                val league = League.fromQueryString(leagueString)
                LeagueDetails(
                    navHostController = navHostController,
                    leagueFormViewModel = LeagueFormViewModel(league),
                    isAdd = false
                )
            }
        }
        composable(
            route = "club/{club}",
            arguments = listOf(navArgument("club"){ type = NavType.StringType })
        ){
            it.arguments?.getString("club")?.let {
                clubString ->
                val club = Club.fromQueryString(clubString)
                ClubDetails(
                    navHostController = navHostController,
                    clubFormViewModel = ClubFormViewModel(club),
                    isAdd = false
                )
            }
        }

        //SETTINGS
        composable(
            route = "settings"
        ){
            Settings(admin = admin)
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