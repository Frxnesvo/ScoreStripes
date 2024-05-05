package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.Quantity
import com.example.clientadmin.model.Season
import com.example.clientadmin.model.Enum.Type
import com.example.clientadmin.viewmodels.ProductViewModel
import java.time.Year

@Composable
fun Products(navHostController: NavHostController, productViewModel: ProductViewModel) {
    val products = productViewModel.products.collectAsState(initial = emptyList()) //lista dei prodotti
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 60.dp)
    ) {
        Title()

        Search(name = "Products") { }

        val l : MutableList<Product> = mutableListOf() //TODO sostituire con la lista products
        repeat(10){
            l.add(
                Product(
                    1,
                    "arsenal",
                    "Premier League",
                    Season(Year.now(), Year.now().plusYears(1)),
                    Type.MAN, Category.JERSEY,
                    "maglia bella",
                    null,
                    null,
                    15.00,
                    false,
                    Quantity()
                )
            )
        }

        ProductList(
            products = l,
            navHostController = navHostController
        )
    }

}

@Composable
fun ProductList(products: List<Product>, navHostController: NavHostController) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(products){
            product ->
            ProductItemColumn(product = product) {
                navHostController.navigate("product/${product.id}")
            }
        }
    }
}