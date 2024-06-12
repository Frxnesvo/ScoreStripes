package com.example.clientuser.activity

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.viewmodel.formviewmodel.CustomerFormViewModel

@Composable
fun CustomerProfile(
    navHostController: NavHostController,
    customerFormViewModel: CustomerFormViewModel
){
    val customer = customerFormViewModel.customer.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.Outlined.ChevronLeft
        ) { navHostController.popBackStack() }

        BoxProfilePic(
            name = customer.value.username,
            pic = customer.value.profilePic     //todo prima c'èera Uri.EMPTY, va bene così?p
        )

        Text(
            text = customer.value.username,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.titleMedium
        )

        BoxImage(
            boxTitle = stringResource(id = R.string.orders),
            painter = painterResource(id = R.drawable.my_order)
        ){ navHostController.navigate("orders") }

        BoxImage(
            boxTitle = stringResource(id = R.string.personal_data),
            painter = painterResource(id = R.drawable.personal_data)
        ){ navHostController.navigate("details") }

        BoxImage(
            boxTitle = stringResource(id = R.string.addresses),
            painter = painterResource(id = R.drawable.address)
        ){ navHostController.navigate("addresses") }
    }
}

@Composable
fun BoxProfilePic(name: String, pic: Bitmap){
    val modifier = Modifier
        .clip(RoundedCornerShape(75.dp))
        .size(150.dp)

    if (pic == Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888))
        Box(
            modifier = modifier
                .background(color = colorResource(id = R.color.secondary)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "${name.first()}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    else
        Image(
            bitmap = pic.asImageBitmap(),
            contentDescription = "userImg",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
}