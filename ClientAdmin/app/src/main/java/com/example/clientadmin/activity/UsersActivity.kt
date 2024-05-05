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
import com.example.clientadmin.model.User
import com.example.clientadmin.viewmodels.UserViewModel

@Composable
fun Users(navHostController: NavHostController, userViewModel: UserViewModel) {
    val users = userViewModel.users.collectAsState(initial = emptyList()) //lista degli utenti
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 60.dp)
            .fillMaxSize()
    ) {
        Title()

        Search("USERS") {}

        val l: MutableList<User> = mutableListOf() //TODO sostituire con la lista users

        repeat(20){
            l.add(
                User(
                    1,
                "Gigi",
                "Hadid",
                "aldo@gmail.com",
                "Aldo2002",
                    listOf(),
                    listOf(),
                null)
            )
        }
        UserList(users = l, navHostController)
    }
}

@Composable
fun UserList(users: List<User>, navHostController: NavHostController){
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(users){
            UserItem(user = it){
                navHostController.navigate("user/${it.id}")
            }
        }
    }
}
