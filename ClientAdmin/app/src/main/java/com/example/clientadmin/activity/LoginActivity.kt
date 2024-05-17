package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clientadmin.R
import com.example.clientadmin.model.enumerator.Gender
import kotlinx.coroutines.flow.flowOf

@Composable
fun Login(globalIndex: MutableIntState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary))
            .padding(10.dp),
    ) {
        Back {
            globalIndex.intValue = 0
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //TODO fare il form view model
            val username = remember { mutableStateOf("") }
            val birthDate = remember { mutableStateOf("") }
            val gender = remember { mutableStateOf("${Gender.entries[0]}") }

            Title()

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clickable {
                        //TODO
                    }
                    .background(colorResource(id = R.color.white), shape = RoundedCornerShape(40.dp)),
                contentAlignment = Alignment.Center
            ){
                Icon(Icons.Filled.AddCircle, contentDescription = "add", tint = colorResource(id = R.color.secondary))
            }

            TextFieldString(
                value = username,
                text = "USERNAME",
                keyboardType = KeyboardType.Text,
                onValueChange = {username.value = it}
            )

            TextFieldString(
                value = birthDate,
                text = "BIRTH DATE",
                keyboardType = KeyboardType.Text,
                onValueChange = {birthDate.value = it}
            )

            ComboBox(options = flowOf(Gender.entries), selectedOption = gender) {
                gender.value = it
            }

            ButtonCustom(text = "LOGIN", background = R.color.secondary){
                globalIndex.intValue = 2
            }
        }
    }
}