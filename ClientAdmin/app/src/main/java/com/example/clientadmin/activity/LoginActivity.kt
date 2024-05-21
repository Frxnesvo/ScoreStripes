package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.viewmodels.LoginFormViewModel
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

@Composable
fun Login(navController : NavHostController, loginFormViewModel: LoginFormViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp),
    ) {
        Back {
            navController.navigate("index")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val adminState by loginFormViewModel.adminState.collectAsState()

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

            TextFieldString(
                value = mutableStateOf(adminState.username),
                text = "USERNAME",
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateUsername(it)
            }

            TextFieldString( //TODO
                value = mutableStateOf(adminState.birthdate.toString()),
                text = "BIRTH DATE",
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateBirthdate(LocalDate.now())
            }

            ComboBox(
                options = flowOf(Gender.entries),
                selectedOption = mutableStateOf(adminState.gender.toString())
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            ButtonCustom(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary) {
                navController.navigate("scaffold")
            }
        }
    }
}