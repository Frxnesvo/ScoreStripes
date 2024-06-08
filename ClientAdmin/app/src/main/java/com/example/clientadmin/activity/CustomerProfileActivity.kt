package com.example.clientadmin.activity

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.CustomerSummary

@Composable
fun CustomerProfile(customerSummary: CustomerSummary, navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Back { navHostController.popBackStack() }

        BoxProfilePic(name = customerSummary.username, pic = customerSummary.pic)

        Text(
            text = customerSummary.username,
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal)
        )

        BoxImage(
            boxTitle = stringResource(id =R.string.list_orders),
            painter = painterResource(id = R.drawable.colombia)
        ){ navHostController.navigate("userOrders/${customerSummary.id}") }

        BoxImage(
            boxTitle = stringResource(id =R.string.personal_data),
            painter = painterResource(id = R.drawable.match)
        ){ navHostController.navigate("userDetails/${customerSummary.id}") }

        BoxImage(
            boxTitle = stringResource(id =R.string.list_addresses),
            painter = painterResource(id = R.drawable.united)
        ){ navHostController.navigate("userAddresses/${customerSummary.id}") }
    }
}

@Composable
fun BoxProfilePic(name: String, pic: Bitmap){
//    if (pic == Uri.EMPTY)
//        Box(
//            modifier = modifier
//                .background(color = colorResource(id = R.color.secondary)),
//            contentAlignment = Alignment.Center
//        ){
//            Text(
//                text = "${name.first()}",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    else
        Image(
            bitmap = pic.asImageBitmap(),
            contentDescription = "userImg",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(75.dp))
                .size(150.dp)
        )
}