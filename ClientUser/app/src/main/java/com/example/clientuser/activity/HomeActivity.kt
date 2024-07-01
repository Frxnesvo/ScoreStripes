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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.League
import com.example.clientuser.model.Product
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.ProductsViewModel
import com.example.clientuser.viewmodel.formviewmodel.CustomerFormViewModel

@Composable
fun Home(
    customerFormViewModel: CustomerFormViewModel,
    leagueViewModel: LeagueViewModel,
    navHostController: NavHostController,
    productsViewModel: ProductsViewModel,
    productViewModel: ProductViewModel
){
    val customer by customerFormViewModel.customer.collectAsState()
    val leagues = leagueViewModel.leagues.collectAsState(initial = emptyList())

    val bestSellerJersey by productsViewModel.moreSoldJersey.collectAsState(initial = emptyList())
    val bestSellerShorts by productsViewModel.moreSoldShorts.collectAsState(initial = emptyList())

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){
        Title()

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = R.string.greetings),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = customer.username,
                color = colorResource(id = R.color.secondary),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        BoxImage(
            height = 300.dp,
            boxTitle = stringResource(id = R.string.discover),
            painter = painterResource(id = R.drawable.home)
        ) { navHostController.navigate("list") }

        Section(
            name = stringResource(id = R.string.best_seller_jersey),
            items = bestSellerJersey,
            navHostController = navHostController,
            productViewModel = productViewModel
        )

        Section(
            name = stringResource(id = R.string.best_seller_shorts),
            items = bestSellerShorts,
            navHostController = navHostController,
            productViewModel = productViewModel
        )

        Section(
            name = stringResource(id = R.string.buy_by_league),
            items = leagues.value,
            navHostController = navHostController,
            productViewModel = productViewModel
        )
    }
}

@Composable
fun Section(
    name: String,
    items: List<Any>,
    navHostController: NavHostController,
    productViewModel: ProductViewModel
){
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
                            productViewModel.getProduct(it.id)
                            ProductItem(it){ navHostController.navigate("product/${it.id}") }
                        }
                        is League -> key(it.id) {
                            LeagueItem(it){ navHostController.navigate("list/league/${it.name}") }
                        }
                    }
                }
        }
    }
}