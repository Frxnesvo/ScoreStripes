package com.example.clientuser.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientuser.LocalCustomer
import com.example.clientuser.LocalLeagueViewModel
import com.example.clientuser.LocalProductViewModel
import com.example.clientuser.LocalProductsViewModel
import com.example.clientuser.R
import com.example.clientuser.model.League
import com.example.clientuser.model.Product

@Composable
fun Home(
    navHostController: NavHostController,
){
    val leagues = LocalLeagueViewModel.current.leagues.collectAsState(initial = emptyList())

    val bestSellerJersey by LocalProductsViewModel.current.moreSoldJersey.collectAsState(initial = emptyList())
    val bestSellerShorts by LocalProductsViewModel.current.moreSoldShorts.collectAsState(initial = emptyList())

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){
        Title()

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            LocalCustomer.current?.let {
                Text(
                    text = stringResource(id = R.string.greetings),
                    style = TextStyle(fontSize = 20.sp, color = colorResource(id = R.color.black)),
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = it.username,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.secondary)
                    ),
                    fontWeight = FontWeight.Bold
                )
            } ?:
            Text(
                text = stringResource(id = R.string.sign_in),
                style = TextStyle(fontSize = 20.sp, color = colorResource(id = R.color.black)),
                fontWeight = FontWeight.Normal
            )
        }

        BoxImage(
            height = 250.dp,
            boxTitle = stringResource(id = R.string.discover),
            painter = painterResource(id = R.drawable.home)
        ) { navHostController.navigate("list") }

        Section(
            name = stringResource(id = R.string.best_seller_jersey),
            items = bestSellerJersey,
            navHostController = navHostController
        )

        Section(
            name = stringResource(id = R.string.best_seller_shorts),
            items = bestSellerShorts,
            navHostController = navHostController
        )

        Section(
            name = stringResource(id = R.string.buy_by_league),
            items = leagues.value,
            navHostController = navHostController
        )
    }
}

@Composable
fun Section(
    name: String,
    items: List<Any>,
    navHostController: NavHostController,
){
    val productsViewModel = LocalProductsViewModel.current
    val productViewModel = LocalProductViewModel.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            if (items.isEmpty())
                item {
                    Text(
                        text = stringResource(id = R.string.nothing_to_show),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            else
                items(items){
                    when (it) {
                        is Product -> key(it.id) {
                            ProductItem(it){
                                productViewModel.getProduct(it.id)
                                navHostController.navigate("product/${it.id}")
                            }
                        }
                        is League -> key(it.id) {
                            LeagueItem(it){
                                productsViewModel.setFilter(mapOf("league" to it.name))
                                navHostController.navigate("list/league/${it.name}")
                            }
                        }
                    }
                }
        }
    }
}