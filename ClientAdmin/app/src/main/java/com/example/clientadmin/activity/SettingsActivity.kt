package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
            value = admin.username,
            text = stringResource(id = R.string.username),
            readOnly = true
        ) { }

        CustomTextField(
            value = admin.firstName,
            text = stringResource(id = R.string.first_name),
            readOnly = true
        ) { }

        CustomTextField(
            value = admin.lastName,
            text = stringResource(id = R.string.last_name),
            readOnly = true
        ) { }

        CustomTextField(
            value = admin.email,
            text = stringResource(id = R.string.email),
            readOnly = true
        ) { }

        CustomTextField(
            value = "${admin.birthDate.dayOfMonth} ${admin.birthDate.month} ${admin.birthDate.year}",
            text = stringResource(id = R.string.birth_date),
            readOnly = true
        ) { }

        CustomTextField(
            value = admin.gender.name,
            text = stringResource(id = R.string.gender),
            readOnly = true
        ) { }
    }
}