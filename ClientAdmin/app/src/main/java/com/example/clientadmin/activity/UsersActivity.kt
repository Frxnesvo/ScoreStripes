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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.model.Customer
import com.example.clientadmin.viewmodels.CustomerViewModel

@Composable
fun Users(navHostController: NavHostController, customerViewModel: CustomerViewModel) {
    val users = customerViewModel.users.collectAsState(initial = emptyList()) //lista degli utenti
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 60.dp)
            .fillMaxSize()
    ) {
        Title()

        Search("USERS") {}

        UserList(customers = listOf(), navHostController)
    }
}

@Composable
fun UserList(customers: List<Customer>, navHostController: NavHostController){
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(customers){
            UserItem(customer = it){
                navHostController.navigate("user/${it.id}")
            }
        }
    }
}
