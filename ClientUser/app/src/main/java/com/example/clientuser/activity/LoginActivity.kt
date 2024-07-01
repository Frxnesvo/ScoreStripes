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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
        val registered by loginViewModel.goToRegister

        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.AutoMirrored.Outlined.KeyboardArrowLeft
        ) {
            loginViewModel.goToLogin()
            navController.navigate("index")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()

            //TODO image picker e combo box non si vedono bene
            ImagePicker(
                image = customerState.profilePic,
                isError = customerState.isProfilePicError,
                errorMessage = stringResource(id = R.string.pic_error),
                size = 150.dp
            ){
                loginFormViewModel.updateProfilePic(it)
            }

            CustomTextField(
                value = customerState.username,
                isError = customerState.isUsernameError,
                errorMessage = stringResource(id = R.string.username_error),
                text = stringResource(id = R.string.username)
            ){
                loginFormViewModel.updateUsername(it)
            }

            CustomDatePicker(
                text = stringResource(id = R.string.birth_date),
                isError = customerState.isBirthdateError,
                errorMessage = stringResource(id = R.string.date_error),
                date = customerState.birthdate
            ){
                loginFormViewModel.updateBirthdate(it)
            }

            CustomComboBox(
                options = Gender.entries,
                text = stringResource(id = R.string.gender),
                selectedOption = customerState.gender.name,
            ) {
                loginFormViewModel.updateGender(Gender.valueOf(it))
            }

            CustomComboBox(
                options = clubs.value,
                expandable = clubs.value.isNotEmpty(),
                text = stringResource(id = R.string.club),
                selectedOption = if (clubs.value.isNotEmpty()) clubs.value[0] else ""
            ) {
                loginFormViewModel.updateFavouriteTeam(it)
            }


            //TODO gestire i vari
            CustomTextField(
                value = addressState.state,
                isError = addressState.isStateError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.state)
            ){
                addressFormViewModel.updateState(it)
            }

            CustomTextField(
                value = addressState.city,
                isError = addressState.isCityError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.city)
            ){
                addressFormViewModel.updateCity(it)
            }

            CustomTextField(
                value = addressState.street,
                isError = addressState.isStreetError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.street)
            ){
                addressFormViewModel.updateStreet(it)
            }

            CustomTextField(
                value = addressState.zipCode,
                isError = addressState.isZipCodeError,
                errorMessage = stringResource(id = R.string.zip_code_error),
                text = stringResource(id = R.string.zip_code),
                keyboardType = KeyboardType.Decimal
            ){
                addressFormViewModel.updateZipCode(it)
            }

            CustomTextField(
                value = addressState.civicNumber,
                isError = addressState.isCivicNumberError,
                errorMessage = stringResource(id = R.string.civic_number_error),
                text = stringResource(id = R.string.civic_number)
            ){
                addressFormViewModel.updateCivicNumber(it)
            }

            val isError  = customerState.isBirthdateError || customerState.isUsernameError || customerState.isProfilePicError
                    || addressState.isStateError || addressState.isCityError || addressState.isStreetError || addressState.isZipCodeError
                    || addressState.isCivicNumberError

            CustomButton(
                enabled = !isError,
                text = stringResource(id = R.string.sign_up),
                background = if (isError) R.color.black50 else R.color.secondary,
            ) {

                //TODO controllare perchè non naviga alla login quando è true
                val club = if(customerState.favouriteTeam != "") customerState.favouriteTeam else clubs.value[0]
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
                    pic = customerState.profilePic!!
                )
            }
        }

        if (!registered){
            navController.navigate("index")
        }
    }
}