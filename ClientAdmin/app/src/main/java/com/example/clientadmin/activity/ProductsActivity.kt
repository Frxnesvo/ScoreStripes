package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductViewModel

@Composable
fun Products(navHostController: NavHostController, productViewModel: ProductViewModel, leagueViewModel: LeagueViewModel) {
    val products = productViewModel.productSummaries.value
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val usernameToSearch = remember { mutableStateOf("") }

    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
    ) {
        item { Title() }

        item { Search(name = stringResource(R.string.list_all_products)) { setBottomSheet(true) } }

        if(products.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.list_all_products_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp)
                )
            }
        else {
            items(products) { product ->
                key(product.id) {
                    ProductItemColumn(product = product) { navHostController.navigate("product/${product.id}") }
                }
            }

            item {
                TextButton(onClick = { productViewModel.incrementPage() }) {
                    Text(
                        text = "enter as guest",
                        color = colorResource(id = R.color.white50),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 5.sp
                        )
                    )
                }
            }
        }
    }

    if (isOpenSheet)
        SearchPanelProducts(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            leagueViewModel = leagueViewModel,
            productViewModel = productViewModel
        )
}