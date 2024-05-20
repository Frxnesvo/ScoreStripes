package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientadmin.R
import com.example.clientadmin.model.Admin
import kotlinx.coroutines.flow.flowOf

@Composable
fun Settings(admin: Admin){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        TextFieldString(
            value = remember { mutableStateOf(admin.username) },
            text = stringResource(id = R.string.username),
            readOnly = true
        ) {
            TODO()
        }

        TextFieldString(
            value = remember { mutableStateOf(admin.firstName) },
            text = stringResource(id = R.string.first_name),
            readOnly = true
        ) {
            TODO()
        }

        TextFieldString(
            value = remember { mutableStateOf(admin.lastName) },
            text = stringResource(id = R.string.last_name)
        ) {
            TODO()
        }

        TextFieldString(
            value = remember { mutableStateOf(admin.email) },
            text = stringResource(id = R.string.email)
        ) {
            TODO()
        }

        TextFieldString(
            value = remember { mutableStateOf("${admin.birthDate}") }, //TODO vedere come gestire le date, probabilmente con un date picker
            text = stringResource(id = R.string.birth_date)
        ) {
            TODO()
        }

        ComboBox(
            options = flowOf(listOf(admin.gender)),
            selectedOption = remember { mutableStateOf("${admin.gender}") }
        ) {
            TODO()
        }
    }
}