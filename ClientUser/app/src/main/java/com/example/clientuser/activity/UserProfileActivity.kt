package com.example.clientuser.activity
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun UserProfile(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp) //gap tra gli elementi
    ){
        Title()
        ImageProfile(150)

        //username
        Text(
            style = TextStyle(
                fontSize = 20.sp,
                letterSpacing = 3.sp),
            text = "FRANCESCO SCHETTINO", //TODO username utente
            fontWeight = FontWeight.Bold
        )

        GeneralButton(height = 130, imageId = R.drawable.my_order, text = "I MIEI ORDINI", R.color.white, {})
        GeneralButton(height = 130, imageId = R.drawable.personal_data, text = "DATI PERSONALI", R.color.white, {})
        GeneralButton(height = 130, imageId = R.drawable.address, text = "INDIRIZZI", R.color.white, {})
    }
}
