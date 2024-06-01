package com.example.clientuser.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.dto.CustomerProfileDto


@Composable
fun UserDetails(customer: CustomerProfileDto, navHostController: NavHostController){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ){
        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.Outlined.ChevronLeft
        ) { navHostController.popBackStack() }

        Text(
            text = stringResource(id = R.string.addresses),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Details(customer = customer)
    }
}

@Composable
fun Details(customer: CustomerProfileDto){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.details),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.edit),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.secondary),
                modifier = Modifier.clickable { TODO("readonly = false oppure readonly = true e update dell'utente") }
            )
        }

        CustomTextField(
            value = customer.firstName,
            text = stringResource(id = R.string.first_name),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.lastName,
            text = stringResource(id = R.string.last_name),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.email,
            text = stringResource(id = R.string.email),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.favoriteClub,
            text = stringResource(id = R.string.favorite_club),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.gender.name,
            text = stringResource(id = R.string.gender),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.birthDate,
            text = "GENDER",
            readOnly = true
        ){}
    }
}

