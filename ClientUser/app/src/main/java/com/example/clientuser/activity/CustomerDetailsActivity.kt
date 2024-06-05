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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.viewmodel.ClubViewModel
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel


@Composable
fun UserDetails(
    loginFormViewModel: LoginFormViewModel,
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    clubViewModel: ClubViewModel
){
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

        Details(
            loginFormViewModel = loginFormViewModel,
            clubViewModel = clubViewModel
        ){
            loginViewModel.updateCustomer(
                TODO("vedere come gestire l'invio del dto")
            )
        }
    }
}

@Composable
fun Details(
    loginFormViewModel: LoginFormViewModel,
    clubViewModel: ClubViewModel,
    onClick: () -> Unit
){
    val customer by loginFormViewModel.customerState.collectAsState()
    val clubsName by clubViewModel.clubNames.collectAsState(initial = emptyList())

    val isEditable = remember { mutableStateOf(false) }

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
                text = stringResource(id = if(!isEditable.value) R.string.edit else R.string.update),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.secondary),
                modifier = Modifier.clickable {
                    if(isEditable.value) onClick()
                    isEditable.value = !isEditable.value
                }
            )
        }

        CustomTextField(
            value = customer.username,
            isError = customer.isUsernameError,
            text = stringResource(id = R.string.username),
            //TODO readOnly = true
        ){}

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

        CustomComboBox(
            options = clubsName,
            selectedOption = customer.favouriteTeam,
        ){
            loginFormViewModel.updateFavouriteTeam(it)
        }

        CustomTextField(
            value = customer.gender.name,
            text = stringResource(id = R.string.gender),
            readOnly = true
        ){}

        CustomTextField(
            value = customer.birthdate.toString(),
            text = stringResource(id = R.string.birth_date),
            readOnly = true
        ){}
    }
}

