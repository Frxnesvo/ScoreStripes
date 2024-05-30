package com.example.clientadmin.activity

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun Login(token: String, navController : NavHostController, loginViewModel: LoginViewModel, loginFormViewModel: LoginFormViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val adminState by loginFormViewModel.adminState.collectAsState()
        val context = LocalContext.current

        Back {
            navController.navigate("index")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()

            ImagePicker(
                imageUri = adminState.profilePic,
                size = 80.dp
            ){
                uri ->
                if (uri != null) {
                    loginFormViewModel.updateProfilePic(uri)
                }
            }

            CustomTextField(
                value = adminState.username,
                text = stringResource(id = R.string.username)
            ){
                loginFormViewModel.updateUsername(it)
            }

            CustomTextField(
                value = adminState.firstName,
                text = stringResource(id = R.string.first_name)
            ){
                loginFormViewModel.updateFirstName(it)
            }

            CustomTextField(
                value = adminState.lastName,
                text = stringResource(id = R.string.last_name)
            ){
                loginFormViewModel.updateLastName(it)
            }

            CustomDatePicker(
                date = remember { mutableStateOf(adminState.birthdate) },
                text = stringResource(id = R.string.birth_date)
            ){
                loginFormViewModel.updateBirthdate(it)
            }

            CustomComboBox(
                options = flowOf(Gender.entries),
                selectedOption = remember { mutableStateOf(adminState.gender.toString()) }
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {
                Log.e("prova debug", "ciccio")
                loginViewModel.register(
                    token = token,
                    AdminCreateRequestDto(
                        username = adminState.username,
                        firstName = adminState.firstName,
                        lastName = adminState.lastName,
                        birthDate = adminState.birthdate,
                        gender = adminState.gender
                    ),
                    context = context,
                    pic = adminState.profilePic
                )

                if (loginViewModel.user.value != null){
                    Log.e("stampa utente", "not null")
                    navController.navigate("scaffold/${token}")
                }
                Log.e("stampa utente", "null")

            }
        }
    }
}