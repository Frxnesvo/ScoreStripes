package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.ProductDto
import com.example.clientadmin.viewmodels.CustomerViewModel
import com.example.clientadmin.viewmodels.HomeViewModel

@Composable
fun Home(selectedScreen: MutableState<Screen>, homeViewModel: HomeViewModel, navHostController: NavHostController) {
    val numNewOrders = homeViewModel.countNewOrders().collectAsState(initial = 0).value
    val numProductOutOfStoke = homeViewModel.countVariantsOutOfStock().collectAsState(initial = 0).value
    val numNewUser = homeViewModel.countNewAccounts().collectAsState(initial = 0).value

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title()

        Stats(
            numNewOrders = numNewOrders,
            numProductOutOfStoke = numProductOutOfStoke.toLong(),
            numNewUser = numNewUser
        )

        BoxImage(
            boxTitle = stringResource(id = R.string.list_all_user),
            painter = painterResource(id = R.drawable.match)
        ){
            selectedScreen.value = Screen.USERS
            navHostController.navigate("users")
        }

        BoxImage(
            boxTitle = stringResource(id = R.string.list_all_products),
            painter = painterResource(id = R.drawable.psg)
        ){
            selectedScreen.value = Screen.PRODUCTS
            navHostController.navigate("products")
        }

        Section(
            nameSection = stringResource(id = R.string.best_sellers),
            products = listOf(), //TODO
            navHostController = navHostController
        )

        Section(
            nameSection = stringResource(id = R.string.least_sold),
            products = listOf(), //TODO
            navHostController = navHostController
        )
    }
}

@Composable
fun Stats(numNewOrders: Long, numProductOutOfStoke: Long, numNewUser: Long){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(id = R.color.white), shape = RoundedCornerShape(30.dp))
            .padding(10.dp))
    {
        Stat(
            text = stringResource(id = R.string.list_new_orders),
            value = numNewOrders
        ){}

        Stat(
            text = stringResource(id = R.string.list_out_of_stock),
            value = numProductOutOfStoke
        ){}

        Stat(
            text = stringResource(id = R.string.list_new_users),
            value = numNewUser
        ){}
    }
}

@Composable
fun Stat(text: String, value: Long, onClick: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Light)
        )
        Text(
            text = "$value",
            color = Color.Red,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp)
        )
    }
}

@Composable
fun Section(nameSection: String, products: List<ProductDto>, navHostController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth())
    {
        Text(
            text = nameSection,
            color = Color.Black,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(700),
                letterSpacing = 5.sp
            )
        )

        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(products){
                product ->
                key(product.id) {
                    ProductItemRow(product) { navHostController.navigate("product/${product.id}") }
                }
            }
        }
    }
}