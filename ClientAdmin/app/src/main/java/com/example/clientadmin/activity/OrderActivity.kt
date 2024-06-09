package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
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
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.enumerator.OrderStatus


@Composable
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white50), RoundedCornerShape(20.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ){
        val style12 = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
        val style8 = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light, color = colorResource(id = R.color.black50))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ORDER", color = colorResource(id = R.color.black), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = order.id.substring(0,6), style = style12)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "AMOUNT: ", style = style8)
                Text(text = "${order.totalPrice}", color = colorResource(id = R.color.secondary), style = style12)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "STREET: ", style = style8)
            Text(text = "${order.resilientInfos.street}, ${order.resilientInfos.civicNumber}", color = colorResource(id = R.color.black), style = style12)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "STATUS: ", style = style8)
            Text(text = "${order.status}",
                color = colorResource(
                    id = when(order.status){
                        OrderStatus.CANCELLED -> R.color.red
                        OrderStatus.PENDING -> R.color.yellow
                        OrderStatus.COMPLETED -> R.color.green
                    }),
                style = style12
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "DATE: ", style = style8)
            Text(text = "${order.date}", color = colorResource(id = R.color.black), style = style12)
        }

        OrderProductsList(orderItems = order.items)
    }
}

@Composable
fun OrderProductsList(orderItems: List<Order.OrderItem>){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(orderItems) {
            orderItem ->
            key(orderItem.id) {
                ProductOrderItem(orderItem = orderItem)
            }
        }
    }
}

@Composable
fun ProductOrderItem(orderItem: Order.OrderItem) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = orderItem.product.product.pic1.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = orderItem.product.product.name,
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(
            text = "QUANTITY: ${orderItem.quantity}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(
            text = "SIZE: ${orderItem.product.size}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
    }
}