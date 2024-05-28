package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientadmin.R
import com.example.clientadmin.model.Admin

@Composable
fun Settings(admin: Admin){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        CustomTextField(
            value = remember { mutableStateOf(admin.username) } ,
            text = stringResource(id = R.string.username),
            readOnly = true
        ) { }

        CustomTextField(
            value = remember { mutableStateOf(admin.firstName) },
            text = stringResource(id = R.string.first_name),
            readOnly = true
        ) { }

        CustomTextField(
            value = remember { mutableStateOf(admin.lastName) },
            text = stringResource(id = R.string.last_name),
            readOnly = true
        ) { }

        CustomTextField(
            value = remember { mutableStateOf(admin.email) },
            text = stringResource(id = R.string.email),
            readOnly = true
        ) { }

        CustomTextField(
            value = remember { mutableStateOf(admin.birthDate) },
            text = stringResource(id = R.string.birth_date),
            readOnly = true
        ) { }

        CustomTextField(
            value = remember { mutableStateOf(admin.gender) },
            text = stringResource(id = R.string.gender),
            readOnly = true
        ) { }
    }
}