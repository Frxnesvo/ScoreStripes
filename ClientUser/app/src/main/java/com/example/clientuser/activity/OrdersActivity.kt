package com.example.clientuser.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.LocalCustomerViewModel
import com.example.clientuser.R

@Composable
fun Orders(
    navHostController: NavHostController
){
    val orders by LocalCustomerViewModel.current.orders.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp),
        state = rememberLazyListState(),
    ) {

        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                BoxIcon(
                    iconColor = colorResource(id = R.color.secondary),
                    content = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
                ) { navHostController.popBackStack() }
            }

        }

        item {
            Text(
                text = stringResource(id = R.string.orders),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (orders.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        else
            items(orders) {
                order ->
                key(order.id) {
                    OrderItem(order = order, navHostController = navHostController)
                }
            }
    }
}
