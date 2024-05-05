package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.LeagueFormViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductFormViewModel
import com.example.clientadmin.viewmodels.ProductViewModel
import com.example.clientadmin.viewmodels.TeamFormViewModel
import com.example.clientadmin.viewmodels.TeamViewModel
import com.example.clientadmin.viewmodels.UserViewModel

enum class Screen{ HOME, USERS, ADD, PRODUCTS, SETTINGS }

@Composable
fun Scaffold(globalIndex: MutableIntState) {
    val selectedScreen = remember { mutableStateOf(Screen.HOME) }
    val userNavHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(userNavHostController, selectedScreen) },
    ) {
        it.calculateTopPadding()

        NavigationUser(
            navHostController = userNavHostController,
            userViewModel = UserViewModel(),
            productViewModel = ProductViewModel(),
            selectedScreen = selectedScreen
        )

        it.calculateBottomPadding()
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
            indexColor = if (Screen.ADD == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = Icons.Outlined.Add
        ) {
            setBottomSheet(true)
        }
        BottomBarButton(
            indexColor = if (Screen.PRODUCTS == selectedScreen.value) R.color.secondary else R.color.black,
            imageVector = Icons.Outlined.List
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
            AddPanel(onDismissRequest = { setBottomSheet(false) }, setBottomSheet = setBottomSheet, navHostController = navHostController)
    }
}

@Composable
fun BottomBarButton(indexColor: Int, background: Int? = null, imageVector: ImageVector?, function: () -> Unit){
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .clickable(onClick = function)
            .background(if (background != null) colorResource(id = background) else Color.Transparent),
        contentAlignment = Alignment.Center
    ){
        if (imageVector != null)
            Icon(
                imageVector = imageVector,
                contentDescription = "buttonIcon",
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
fun NavigationUser(navHostController: NavHostController, userViewModel: UserViewModel, productViewModel: ProductViewModel, selectedScreen: MutableState<Screen>) {
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
            Users(navHostController = navHostController, userViewModel)
        }
        composable(
            route = "user/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.IntType })
        ){
            it.arguments?.getInt("id")?.let {
                id -> userViewModel.getUser(id).collectAsState(initial = null).value?.let {
                    user -> UserProfile(user = user, navHostController = navHostController)
                }
            }
        }
        composable(
            route = "userOrders/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.IntType })
        ){
            it.arguments?.getInt("id")?.let {
                id -> userViewModel.getUser(id).collectAsState(initial = null).value?.let {
                    user -> Orders(user = user, navHostController = navHostController)
                }
            }
        }
        composable(
            route = "userDetails/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.IntType })
        ){
            it.arguments?.getInt("id")?.let {
                id -> userViewModel.getUser(id).collectAsState(initial = null).value?.let {
                    user -> UserDetails(user = user, navHostController = navHostController)
                }
            }
        }
        composable(
            route = "userAddresses/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.IntType })
        ){
            it.arguments?.getInt("id")?.let {
                id -> userViewModel.getUser(id).collectAsState(initial = null).value?.let {
                    user -> Addresses(user = user, navHostController = navHostController)
                }
            }
        }

        //ADD
        composable(
            route = "addProduct"
        ){
            ProductDetails(productViewModel = productViewModel, productFormViewModel = ProductFormViewModel(), navHostController = navHostController)
        }
        composable(
            route = "addLeague"
        ){
            LeagueDetails(leagueViewModel = LeagueViewModel(), leagueFormViewModel = LeagueFormViewModel(), navHostController = navHostController)
        }
        composable(
            route = "addTeam"
        ){
            TeamDetails(leagueViewModel = LeagueViewModel(), teamViewModel = TeamViewModel(), teamFormViewModel = TeamFormViewModel(), navHostController = navHostController)
        }

        //PRODUCTS
        composable(
            route = "products"
        ){
            Products(navHostController = navHostController, productViewModel = productViewModel)
        }
        composable(
            route= "product/{id}",
            arguments = listOf(navArgument("id"){ type = NavType.IntType })
        ){
            it.arguments?.getInt("id")?.let {
                id -> productViewModel.getProduct(id).collectAsState(initial = null).value?.let {
                    product-> ProductDetails(
                        productViewModel = ProductViewModel(),
                        productFormViewModel = ProductFormViewModel(product),
                        navHostController = navHostController
                    )
                }
            }
        }

        //SETTINGS
        composable(
            route = "settings"
        ){
            //TODO fare la pagina dei settings
        }
    }
}