package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Customer
import com.example.clientadmin.model.Order

@Composable
fun Orders(customer: Customer, navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Back { navHostController.popBackStack() }

        SubSectionUser(customer = customer, subSectionName = "ORDERS")

        OrdersList(orders = listOf(), navHostController = navHostController)
    }
}

@Composable
fun OrdersList(orders: List<Order>?, navHostController: NavHostController){
    if (orders == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "no orders to show",
                color = colorResource(id = R.color.black),
                style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp)
            )
        }
    }
    else {
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(orders) {
                order -> OrderItem(order = order, navHostController = navHostController)
            }
        }
    }
}
