package com.example.clientuser.activity

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.Enum.Gender

@Composable
fun Login(token: String, navController : NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

//        Back {
//            navController.navigate("index")
//        }

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

                }
            }

            CustomTextField(
                value = "adminState.username",
                text = stringResource(id = R.string.username)
            ){

            }

            CustomTextField(
                value = "adminState.firstName",
                text = stringResource(id = R.string.first_name)
            ){

            }

            CustomTextField(
                value = "adminState.lastName",
                text = stringResource(id = R.string.last_name)
            ){

            }

            CustomDatePicker(
                text = stringResource(id = R.string.birth_date)
            ){

            }

            CustomComboBox(
                options = Gender.entries,
                selectedOption = remember { mutableStateOf("adminState.gender.toString()") }
            ) {

            }

            CustomButton(
                text = stringResource(id = R.string.sign_up),
                background = R.color.secondary
            ) {

            }
        }
    }
}