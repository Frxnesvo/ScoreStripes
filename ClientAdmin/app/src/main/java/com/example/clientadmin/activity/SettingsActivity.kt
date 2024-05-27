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
            text = stringResource(id = R.string.last_name)
        ) { }

        CustomTextField(
            value = admin.email,
            text = stringResource(id = R.string.email)
        ) { }

        CustomTextField(
            value = "${admin.birthDate}",
            text = stringResource(id = R.string.birth_date),
            readOnly = true
        ) { }

        CustomTextField(
            value = "${admin.birthDate.dayOfWeek} ${admin.birthDate.month} ${admin.birthDate.year}",
            text = stringResource(id = R.string.gender),
            readOnly = true
        ) { }
    }
}