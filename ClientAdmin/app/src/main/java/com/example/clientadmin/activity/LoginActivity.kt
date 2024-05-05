package com.example.clientadmin.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientadmin.R

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
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Title()

            TextFieldString(
                value = email,
                text = "email",
                keyboardType = KeyboardType.Email,
                onValueChange = {email.value = it}
            )

            TextFieldString(
                value = password,
                text = "password",
                keyboardType = KeyboardType.Password,
                onValueChange = {email.value = it}
            )

            ButtonCustom(text = "LOGIN", background = R.color.secondary){
                //TODO
            }

            Text(
                text = "or",
                color = colorResource(id = R.color.black50),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 5.sp
                )
            )

            TextButton(
                onClick = { /*TODO*/ },
                border = BorderStroke(2.dp, color = colorResource(id = R.color.black)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "WITH GOOGLE",
                    color = colorResource(id = R.color.black),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 5.sp
                    )
                )
            }
        }
    }
}