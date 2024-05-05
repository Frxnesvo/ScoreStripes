package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.clientadmin.R

@Composable
fun IndexPage(globalIndex: MutableIntState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.collection),
            contentDescription = "collection image",
            modifier = Modifier
                .fillMaxSize()
                .blur(10.dp)
        )
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.black50))
                .fillMaxSize()
                .padding(vertical = 50.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Title(colorStripes = colorResource(id = R.color.white))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonCustom(background = R.color.secondary50, text = "SIGN IN") {
                    globalIndex.intValue = 1
                }

                ButtonCustom(background = R.color.black50, text = "SIGN UP") {
                    globalIndex.intValue = 2
                }

                TextButton(onClick = { globalIndex.intValue = 3 }) {
                    Text(
                        text = "enter as guest",
                        color = colorResource(id = R.color.white50),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 5.sp
                        )
                    )
                }
            }
        }
    }
}