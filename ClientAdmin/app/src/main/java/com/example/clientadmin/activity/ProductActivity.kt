package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientadmin.R
import com.example.clientadmin.model.Product

@Composable
fun ProductItemRow(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .background(Color.Transparent)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.arsenal), // TODO prendere da product
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(text = "${product.club}",
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
        )
        Text(text = "${product.productCategory}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light)
        )
    }
}

@Composable
fun ProductItemColumn(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x80FFFFFF), RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "${product.club}", color = Color.Black, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal))
            Text(text = "${product.productCategory}", color = colorResource(id = R.color.black), style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painterResource(R.drawable.arsenal), // TODO prendere da product
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                //TODO - gestire le quantità
                /*for (quantity in product.) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                                .background(Color.Red, RoundedCornerShape(12.5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${quantity.first}",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                                .background(Color.White, RoundedCornerShape(12.5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${quantity.second}",
                                color = Color.Red,
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }*/
            }
        }
    }
}