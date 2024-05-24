package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.CustomerProfileDto


@Composable
fun UserDetails(customer: CustomerProfileDto, navHostController: NavHostController){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ){
        Back { navHostController.popBackStack() }

        Details(customer = customer)
    }
}

@Composable
fun Details(customer: CustomerProfileDto){
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

        CustomTextField(
            value = customer.firstName,
            text = "FIRST NAME",
            readOnly = true
        ){}

        CustomTextField(
            value = customer.lastName,
            text = "LAST NAME",
            readOnly = true
        ){}

        CustomTextField(
            value = customer.email,
            text = "EMAIL",
            readOnly = true
        ){}

        CustomTextField(
            value = customer.favoriteClub,
            text = "FAVORITE CLUB",
            readOnly = true
        ){}

        CustomTextField(
            value = customer.gender,
            text = "GENDER",
            readOnly = true
        ){}

        //TODO inserire la data di nascita
    }
}

