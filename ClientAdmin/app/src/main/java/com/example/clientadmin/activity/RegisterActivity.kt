package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.AdminCreateRequestDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.viewmodels.LoginViewModel
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel

@Composable
fun Register(
    token: String,
    navController : NavHostController,
    loginViewModel: LoginViewModel,
    loginFormViewModel: LoginFormViewModel
) {

    val adminState by loginFormViewModel.adminState.collectAsState()
    val error by loginViewModel.addError
    val registered by loginViewModel.goToRegister

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Back {
            loginViewModel.goToLogin()
            navController.navigate("index")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()

            ImagePicker(
                pic = adminState.profilePic,
                isError = adminState.isProfilePicError,
                errorMessage = stringResource(id = R.string.pic_error),
                size = 150.dp
            ){ loginFormViewModel.updateProfilePic(it) }

            CustomTextField(
                value = adminState.username,
                isError = adminState.isUsernameError,
                errorMessage = stringResource(id = R.string.username_error),
                text = stringResource(id = R.string.username)
            ){
                loginFormViewModel.updateUsername(it)
            }

            CustomDatePicker(
                date = adminState.birthdate,
                isError = adminState.isBirthdateError,
                errorMessage = stringResource(id = R.string.date_error),
                text = stringResource(id = R.string.birth_date)
            ){
                loginFormViewModel.updateBirthdate(it)
            }

            CustomComboBox(
                options = Gender.entries,
                text = stringResource(id = R.string.gender),
                selectedOption = "${adminState.gender}"
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = colorResource(id = R.color.red)
                )
            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = if (adminState.isUsernameError || adminState.isBirthdateError || adminState.isProfilePicError) R.color.black50 else R.color.secondary
            ) {
                if (!adminState.isUsernameError && !adminState.isBirthdateError && !adminState.isProfilePicError)

                    loginViewModel.register(
                        token = token,
                        adminCreateRequestDto = AdminCreateRequestDto(
                            idToken = token,
                            username = adminState.username,
                            birthDate = adminState.birthdate,
                            gender = adminState.gender,
                        ),
                        pic = adminState.profilePic!!
                    )
            }
        }

        if(!registered) navController.navigate("index")
    }
}