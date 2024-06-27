package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientadmin.model.enumerator.FilterType
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.ClubViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductViewModel
import com.example.clientadmin.viewmodels.ProductsViewModel
import com.example.clientadmin.viewmodels.formViewModel.FilterFormViewModel

@Composable
fun Products(
    navHostController: NavHostController,
    productsViewModel: ProductsViewModel,
    productViewModel: ProductViewModel,
    leagueViewModel: LeagueViewModel,
    clubViewModel: ClubViewModel,
    filterFormViewModel: FilterFormViewModel
) {
    val products by productsViewModel.productSummaries.collectAsState()
    val leagues by leagueViewModel.leagues.collectAsState()
    val clubs by clubViewModel.clubs.collectAsState()

    val page by productsViewModel.page.collectAsState()

    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

    var control by remember { mutableStateOf(false) }
    var filterType by remember { mutableStateOf(FilterType.PRODUCTS) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
        modifier = Modifier.fillMaxSize()
    ) {
        item { Title() }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable {
                                control = true
                                filterType = FilterType.PRODUCTS
                            }
                    ){
                        Text(
                            text = filterType.name,
                            color = colorResource(id = R.color.black),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp)
                        )
                        Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = control, onDismissRequest = { control = false }) {
                        FilterType.entries.forEach {
                            if (it != FilterType.CUSTOMERS)
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.name,
                                            color = colorResource(id = R.color.black),
                                            style = TextStyle(fontSize = 12.sp, letterSpacing = 5.sp)
                                        )
                                    },
                                    onClick = {
                                        filterType = it
                                        control = false
                                    }
                                )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(colorResource(id = R.color.white), CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { setBottomSheet(true) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = colorResource(id = R.color.secondary))
                }
            }
        }

        if(
            (filterType == FilterType.PRODUCTS && products.isEmpty()) ||
            (filterType == FilterType.CLUBS && clubs.isEmpty()) ||
            (filterType == FilterType.LEAGUES && leagues.isEmpty())
        )
            item{
                Text(
                    text = stringResource(id = R.string.list_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        else {
            if (FilterType.PRODUCTS == filterType) {
                items(products) {
                    key(it.id) {
                        ProductItemColumn(productSummary = it) {
                            productViewModel.getProduct(it.id)
                            navHostController.navigate("product/${it.id}")
                        }
                    }
                }
            } else if (FilterType.CLUBS == filterType) {
                items(clubs) {
                    key(it.name) {
                        ClubItem(club = it) { navHostController.navigate("club/${it.toQueryString()}") }
                    }
                }
            } else if (FilterType.LEAGUES == filterType) {
                items(leagues) {
                    key(it.name) {
                        LeagueItem(league = it) { navHostController.navigate("league/${it.toQueryString()}") }
                    }
                }
            }

            item {
                if (FilterType.PRODUCTS == filterType && page.number < page.totalPages)
                    Text(
                        text = stringResource(id = R.string.more),
                        color = colorResource(id = R.color.black50),
                        style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ){productsViewModel.incrementPage() }
                    )
            }
        }
    }

    if (isOpenSheet && FilterType.LEAGUES == filterType)
        SearchByNameSheet(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            filterFormViewModel = filterFormViewModel,
            filterType = filterType
        ){ leagueViewModel.setFilter(it) }
    else if (isOpenSheet)
        SearchPanelProducts(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            leagues = leagues.map { it.name },
            filterFormViewModel = filterFormViewModel,
            filterType = filterType
        ){
            if (FilterType.PRODUCTS == filterType)
                productsViewModel.setFilter(it)
            else
                clubViewModel.setFilter(it)
        }
}