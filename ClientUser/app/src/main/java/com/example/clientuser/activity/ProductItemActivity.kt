package com.example.clientuser.activity

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R
import com.example.clientuser.model.BasicProduct
import com.example.clientuser.model.Product

@Composable
fun ProductItem(product: Any, onClick: () -> Unit) {
    val image: Bitmap
    val category: String
    val name: String

    when(product){
        is Product -> {
            image = product.pic1
            category = product.productCategory.name
            name = product.name
        }
        is BasicProduct -> {
            image = product.pic
            category = product.category.name
            name = product.name
        }
        else -> {
            image = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            category = ""
            name = ""
        }
    }

    Column(
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = name,
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
        )
        Text(
            text = category,
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light)
        )
    }
}