package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientadmin.R
import com.example.clientadmin.authentication.LogoutManager
import com.example.clientadmin.model.Admin

@Composable
fun Settings(admin: Admin){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Title()

//        Image(
//            bitmap = admin.pic.asImageBitmap(),
//            contentDescription = null,
//            modifier = Modifier.size(150.dp).clip(shape = CircleShape)
//        )

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

        CustomButton(
            textColor = colorResource(id = R.color.secondary50),
            text = stringResource(id = R.string.logout),
            background = R.color.black50
        ) {
            LogoutManager.instance.logout()
        }
    }
}