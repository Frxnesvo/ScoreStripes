package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.clientadmin.R
import com.example.clientadmin.model.User

@Composable
fun UserProfile(user: User, navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Back { navHostController.popBackStack() }

        Image(
            painter = painterResource(id = R.drawable.united),
            contentDescription = "userImg",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(75.dp))
                .size(150.dp)
                .clickable {
                    //TODO modifica immagine
                }
        )

        Text(text = "${user.name} ${user.surname}", color = colorResource(id = R.color.black), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal))

        BoxImage(boxTitle = "ORDINI", painter = painterResource(id = R.drawable.colombia)){
            navHostController.navigate("userOrders/${user.id}")
        }

        BoxImage(boxTitle = "DATI PERSONALI", painter = painterResource(id = R.drawable.match)){
            navHostController.navigate("userDetails/${user.id}")
        }

        BoxImage(boxTitle = "INDIRIZZI", painter = painterResource(id = R.drawable.united)){
            navHostController.navigate("userAddresses/${user.id}")
        }
    }
}