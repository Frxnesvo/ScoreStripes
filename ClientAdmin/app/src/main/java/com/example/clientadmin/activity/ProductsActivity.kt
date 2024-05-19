package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Product
import com.example.clientadmin.viewmodels.ProductViewModel
import java.time.Year

@Composable
fun Products(navHostController: NavHostController, productViewModel: ProductViewModel) {
    val products = productViewModel.products.collectAsState(initial = emptyList()).value
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
    ) {
        item { Title() }

        item { Search(name = stringResource(R.string.list_all_products)) { } }

        if(products.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.list_all_products_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp)
                )
            }
        else
            items(products){
                product -> ProductItemColumn(product = product) { navHostController.navigate("product/${product.id}") }
            }
    }
}