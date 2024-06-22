package com.example.clientuser.activity

import android.graphics.Bitmap
import android.util.Log
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
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel

@Composable
fun Login(
    token: String,
    navController : NavHostController,
    loginViewModel: LoginViewModel,
    loginFormViewModel: LoginFormViewModel,
    clubViewModel: ClubViewModel,
    addressFormViewModel: AddressFormViewModel
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
        val addressState by addressFormViewModel.addressState.collectAsState()

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
                selectedOption = customerState.gender.name,
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

            CustomTextField(
                value = addressState.state,
                isError = addressState.isStateError,
                text = stringResource(id = R.string.state)
            ){
                addressFormViewModel.updateState(it)
            }

            CustomTextField(
                value = addressState.city,
                isError = addressState.isCityError,
                text = stringResource(id = R.string.city)
            ){
                addressFormViewModel.updateCity(it)
            }

            CustomTextField(
                value = addressState.street,
                isError = addressState.isStreetError,
                text = stringResource(id = R.string.street)
            ){
                addressFormViewModel.updateStreet(it)
                Log.e("prova stato", "update: $it")
            }

            CustomTextField(
                value = addressState.zipCode,
                isError = addressState.isZipCodeError,
                text = stringResource(id = R.string.zip_code)
            ){
                addressFormViewModel.updateZipCode(it)
            }

            CustomTextField(
                value = addressState.civicNumber,
                isError = addressState.isCivicNumberError,
                text = stringResource(id = R.string.civic_number)
            ){
                addressFormViewModel.updateCivicNumber(it)
            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {
                val club = if(customerState.favouriteTeam != "") customerState.favouriteTeam else clubs.value[0]
                println("FAVOURITE TEAM: $club")
                if (

                    loginViewModel.register(
                        token = token,
                        username = customerState.username,
                        birthDate = customerState.birthdate,
                        favouriteTeam = club,
                        gender = customerState.gender,
                        address = Address(
                            state = addressState.state,
                            street = addressState.street,
                            city = addressState.city,
                            civicNumber = addressState.civicNumber,
                            zipCode = addressState.zipCode,
                            defaultAddress = true
                        ),
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