package com.example.clientuser.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.Wishlist
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.ProductsViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.FilterFormViewModel

@Composable
fun WishlistProducts(
    name: String,
    items: Wishlist,
    navHostController: NavHostController,
    productViewModel: ProductViewModel
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            BoxIcon(
                iconColor = colorResource(id = R.color.secondary),
                content = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
            ) { navHostController.popBackStack() }
        }


        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(25.dp)
        ) {
            items(items.items){
                key(it.product.id) {
                    ProductItem(it.product){
                        productViewModel.getProduct(it.product.id)
                        navHostController.navigate("product/${it.product.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun WishlistDiscover(
    wishListViewModel: WishListViewModel,
    navHostController: NavHostController
){
    val publicWishLists = wishListViewModel.publicWishLists.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                BoxIcon(
                    iconColor = colorResource(id = R.color.secondary),
                    content = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
                ) { navHostController.popBackStack() }
            }

        }

        item {
            Text(
                text = stringResource(id = R.string.discover_wishlists),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(publicWishLists.value){
            key(it.id) {
                WishListItem(it){ navHostController.navigate("publicWishlistProducts/${it.id}") }
            }
        }
    }
}

@Composable
fun ListDiscover(
    name: String,
    productsViewModel: ProductsViewModel,
    leagueViewModel: LeagueViewModel,
    navHostController: NavHostController,
    productViewModel: ProductViewModel
){
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val products by productsViewModel.products.collectAsState()

    val page by productsViewModel.page.collectAsState()


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding = PaddingValues(25.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            IconButtonBar(
                imageVector = Icons.Outlined.Search,
                navHostController = navHostController
            ) { setBottomSheet(true) }
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (products.isEmpty())
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(id = R.string.list_empty),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        else
            items(products){
                key(it.id) {
                    productViewModel.getProduct(it.id)
                    ProductItem(it){ navHostController.navigate("product/${it.id}") }
                }
            }

        if (page.number < page.totalPages)
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(id = R.string.more),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ){ productsViewModel.incrementPage() }
                )
            }
    }

    if (isOpenSheet)
        SearchPanelProducts(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            leagues = leagueViewModel.leagues.collectAsState(initial = emptyList()).value.map { it.name },
            filterFormViewModel = FilterFormViewModel(),
            onSearch = { productsViewModel.setFilter(it) }
        )
}