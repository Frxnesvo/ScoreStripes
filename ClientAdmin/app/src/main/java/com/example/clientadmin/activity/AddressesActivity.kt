package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Address
import com.example.clientadmin.model.User

@Composable
fun Addresses(user: User, navHostController: NavHostController){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(Color.Transparent)
    ){
        Back { navHostController.popBackStack() }

        SubSectionUser(user = user, subSectionName = "ADDRESSES")

        AddressesList(addresses = user.addresses)
    }
}

@Composable
fun AddressesList(addresses: List<Address>){
    val listState = rememberLazyListState()
    if (addresses.isEmpty())
        Text(text = "no address to show", color = colorResource(id = R.color.black), style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp))
    else
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(addresses){
                AddressItem(address = it)
            }
        }
}