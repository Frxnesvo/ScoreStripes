package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.User


@Composable
fun UserDetails(user: User, navHostController: NavHostController){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
    ){
        Back { navHostController.popBackStack() }

        SubSectionUser(user = user, subSectionName = "PERSONAL DATA")

        //User(2, "aldo", "gioia", "aldo@gmail.com", "Aldo@021", listOf(), listOf(), null)

        Details(user = user)
    }
}

@Composable
fun Details(user: User){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ){
        Text(
            text = "Details",
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, letterSpacing = 5.sp)
        )

        TextFieldString(
            value = remember{ mutableStateOf(user.name) },
            onValueChange = { },
            text = "NAME",
            readOnly = true
        )

        TextFieldString(
            value = remember{ mutableStateOf(user.surname) },
            onValueChange = { },
            text = "SURNAME",
            readOnly = true
        )

        TextFieldString(
            value = remember{ mutableStateOf(user.email) },
            onValueChange = { },
            text = "EMAIL",
            readOnly = true
        )
    }
}

