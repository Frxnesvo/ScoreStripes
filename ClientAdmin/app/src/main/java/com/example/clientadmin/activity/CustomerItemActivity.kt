package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.clientadmin.R
import com.example.clientadmin.model.CustomerSummary

@Composable
fun CustomerItem(customerSummary: CustomerSummary, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white50), RoundedCornerShape(25.dp))
            .padding(5.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            bitmap = customerSummary.pic.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = customerSummary.username,
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
        )
    }
}