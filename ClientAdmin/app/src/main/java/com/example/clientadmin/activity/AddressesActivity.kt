package com.example.clientadmin.activity

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Address
import com.example.clientadmin.model.Customer

@Composable
fun Addresses(customer: Customer, navHostController: NavHostController){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
            .fillMaxSize()
            .background(Color.Transparent)
    ){
        Back { navHostController.popBackStack() }

        SubSectionUser(customer = customer, subSectionName = "ADDRESSES")

        AddressesList(addresses = customer.addresses)
    }
}

@Composable
fun AddressesList(addresses: List<Address>){
    if (addresses.isEmpty())
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "no addresses to show",
                color = colorResource(id = R.color.black),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    else
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(addresses){
                address -> AddressItem(address = address)
            }
        }
}