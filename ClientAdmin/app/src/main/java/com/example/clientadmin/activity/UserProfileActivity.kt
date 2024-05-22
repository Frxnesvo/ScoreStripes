package com.example.clientadmin.activity

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clientadmin.R
import com.example.clientadmin.model.CustomerSummary

@Composable
fun UserProfile(customer: CustomerSummary, navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Back { navHostController.popBackStack() }

        BoxProfilePic(name = customer.username, picUri = customer.pic) //TODO profilePic

        Text(text = customer.username, color = colorResource(id = R.color.black), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal))

        BoxImage(boxTitle = "ORDERS", painter = painterResource(id = R.drawable.colombia)){
            navHostController.navigate("userOrders/${customer.id}")
        }

        BoxImage(boxTitle = "PERSONAL DATA", painter = painterResource(id = R.drawable.match)){
            navHostController.navigate("userDetails/${customer.id}")
        }

        BoxImage(boxTitle = "ADDRESSES", painter = painterResource(id = R.drawable.united)){
            navHostController.navigate("userAddresses/${customer.id}")
        }
    }
}

@Composable
fun BoxProfilePic(name: String, picUri: Uri){
    val modifier = Modifier
        .clip(RoundedCornerShape(75.dp))
        .size(150.dp)

    if (picUri == Uri.EMPTY)
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
            painter = rememberAsyncImagePainter(picUri),
            contentDescription = "userImg",
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
}