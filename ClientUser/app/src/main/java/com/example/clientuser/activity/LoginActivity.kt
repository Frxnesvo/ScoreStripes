package com.example.clientuser.activity

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
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
import com.example.clientuser.model.Address
import com.example.clientuser.viewmodel.LoginViewModel
import com.example.clientuser.viewmodel.formviewmodel.LoginFormViewModel
import com.example.clientuser.model.enumerator.Gender
import com.example.clientuser.viewmodel.ClubViewModel

@Composable
fun Login(
    token: String,
    navController : NavHostController,
    loginViewModel: LoginViewModel,
    loginFormViewModel: LoginFormViewModel,
    clubViewModel: ClubViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val customerState by loginFormViewModel.customerState.collectAsState()
        val clubs = clubViewModel.clubNames.collectAsState(initial = emptyList())

        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.AutoMirrored.Outlined.KeyboardArrowLeft
        ) {
            loginViewModel.goToLogin()
            navController.navigate("login")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()

            ImagePicker(
                image = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888),
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

            CustomComboBox(
                options = clubs.value,
                expandable = clubs.value.isNotEmpty(),
                selectedOption = if (clubs.value.isNotEmpty()) clubs.value[0] else ""
            ) {
                loginFormViewModel.updateFavouriteTeam(it)
            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {
                val club = if(customerState.favouriteTeam != "") customerState.favouriteTeam else clubs.value[0]
                if (
                    loginViewModel.register(
                        token = token,
                        username = customerState.username,
                        birthDate = customerState.birthdate,
                        favouriteTeam = club,
                        gender = customerState.gender,
                        address = Address(
                            state = "STATE",
                            street = "STREET",
                            city = "CITY",
                            civicNumber = "13",
                            zipCode = "87036",
                            defaultAddress = false
                        ), //TODO farlo inserire durante la register
                        pic = customerState.profilePic
                    )
                ){
                    loginViewModel.goToLogin()
                    navController.navigate("index")
                }

            }
        }
    }
}