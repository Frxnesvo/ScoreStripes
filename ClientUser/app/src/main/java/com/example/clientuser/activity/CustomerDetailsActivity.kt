package com.example.clientuser.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
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
import com.example.clientuser.authentication.LogoutManager
import com.example.clientuser.viewmodel.ClubViewModel
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.formviewmodel.CustomerFormViewModel


@Composable
fun UserDetails(
    navHostController: NavHostController,
    clubViewModel: ClubViewModel,
    customerFormViewModel: CustomerFormViewModel,
    customerViewModel: CustomerViewModel
){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ){
        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
        ) { navHostController.popBackStack() }

        Text(
            text = stringResource(id = R.string.details),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Details(
            customerFormViewModel = customerFormViewModel,
            clubViewModel = clubViewModel
        ){
            TODO("fare customerViewModel.updateCustomer()")
        }
    }
}

@Composable
fun Details(
    clubViewModel: ClubViewModel,
    customerFormViewModel: CustomerFormViewModel,
    onClick: () -> Unit
){
    val customer by customerFormViewModel.customer.collectAsState()
    val clubsNames by clubViewModel.clubNames.collectAsState(initial = emptyList())
    val isEditable = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ImagePicker(
                image = customer.profilePic,
                size = 100.dp
            ) {
                customerFormViewModel.updateProfilePic(it)
            }
        }

        CustomTextField(
            value = customer.username,
            isError = customer.isUsernameError,
            text = stringResource(id = R.string.username),
            readOnly = true
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
            options = clubsNames,
            text = stringResource(id = R.string.club),
            selectedOption = customer.favouriteTeam,
        ){
            customerFormViewModel.updateFavouriteTeam(it)
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

        CustomButton(
            textColor = colorResource(id = R.color.white),
            text = stringResource(id = R.string.update),
            background = R.color.secondary
        ) {
            LogoutManager.instance.logout()
        }

        CustomButton(
            textColor = colorResource(id = R.color.red),
            text = stringResource(id = R.string.logout),
            background = R.color.black50
        ) {
            LogoutManager.instance.logout()
        }
    }
}

