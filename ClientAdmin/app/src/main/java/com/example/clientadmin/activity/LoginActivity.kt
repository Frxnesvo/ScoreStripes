package com.example.clientadmin.activity

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.service.ConverterUri
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
                value = remember { mutableStateOf(adminState.username) },
                text = stringResource(id = R.string.username),
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateUsername(it)
            }

            CustomTextField(
                value = remember { mutableStateOf(adminState.firstName) },
                text = stringResource(id = R.string.first_name),
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateFirstName(it)
            }

            CustomTextField(
                value = remember { mutableStateOf(adminState.lastName) },
                text = stringResource(id = R.string.last_name),
                keyboardType = KeyboardType.Text
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
                loginViewModel.register(
                    //TODO vedere se fare la richiesta da .request
                    AdminCreateRequestDto(
                        username = adminState.username,
                        firstName = adminState.firstName,
                        lastName = adminState.lastName,
                        birthDate = adminState.birthdate,
                        pic = ConverterUri.convert(context, adminState.profilePic, "pic")!!,
                        gender = adminState.gender
                    )
                )
                if (loginViewModel.user.value != null){
                    navController.navigate("scaffold/${token}")
                }

            }
        }
    }
}