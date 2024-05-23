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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.viewmodels.formViewModel.LoginFormViewModel
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

@Composable
fun Login(firstName: String, lastName: String, navController : NavHostController, loginFormViewModel: LoginFormViewModel) {
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

            /*ImagePicker(
                imageUri = adminState.profilePic,
                size = 80.dp
            ){
                uri ->
                if (uri != null) {
                    loginFormViewModel.updateProfilePic(uri)
                }
            }*/

            TextFieldString(
                value = remember { mutableStateOf(adminState.username) },
                text = stringResource(id = R.string.username),
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateUsername(it)
            }

            if(firstName != ""){
                TextFieldString(
                    value = remember { mutableStateOf(adminState.firstName) },
                    text = stringResource(id = R.string.first_name),
                    keyboardType = KeyboardType.Text
                ){
                    loginFormViewModel.updateFirstName(it)
                }
            } else loginFormViewModel.updateFirstName(firstName)

            if(lastName != ""){
                TextFieldString(
                    value = remember { mutableStateOf(adminState.lastName) },
                    text = stringResource(id = R.string.last_name),
                    keyboardType = KeyboardType.Text
                ){
                    loginFormViewModel.updateLastName(it)
                }
            } else loginFormViewModel.updateLastName(lastName)

            TextFieldString( //TODO
                value = remember { mutableStateOf(adminState.birthdate.toString()) },
                text = stringResource(id = R.string.birth_date),
                keyboardType = KeyboardType.Text
            ){
                loginFormViewModel.updateBirthdate(LocalDate.now())
            }

            ComboBox(
                options = flowOf(Gender.entries),
                selectedOption = remember { mutableStateOf(adminState.gender.toString()) }
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            ButtonCustom(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {
                navController.navigate("scaffold")
            }
        }
    }
}