package com.example.clientuser.activity

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel
import com.example.clientuser.model.enumerator.Gender

@Composable
fun Login(token: String, navController : NavHostController, loginViewModel: LoginViewModel, loginFormViewModel: LoginFormViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val customerState by loginFormViewModel.customerState.collectAsState()

        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.Outlined.ChevronLeft
        ) { navController.popBackStack() }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()

            ImagePicker(
                imageUri = Uri.EMPTY,
                size = 120.dp
            ){
                if (it != null) {
                    loginFormViewModel.updateProfilePic(it)
                }
            }

            CustomTextField(
                value = customerState.username,
                isError = customerState.isUsernameError,
                text = stringResource(id = R.string.username)
            ){
                loginFormViewModel.updateUsername(it)
            }

            CustomTextField(
                value = customerState.firstName,
                isError = customerState.isFirstNameError,
                text = stringResource(id = R.string.first_name)
            ){
                loginFormViewModel.updateFirstName(it)
            }

            CustomTextField(
                value = customerState.lastName,
                isError = customerState.isLastNameError,
                text = stringResource(id = R.string.last_name)
            ){
                loginFormViewModel.updateLastName(it)
            }

            CustomDatePicker(
                text = stringResource(id = R.string.birth_date)
            ){
                loginFormViewModel.updateBirthdate(it)
            }

            CustomComboBox(
                options = Gender.entries,
                selectedOption = customerState.gender.name
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {
                TODO()
            }
        }
    }
}