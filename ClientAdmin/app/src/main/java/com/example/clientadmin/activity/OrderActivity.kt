package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.OrderDto
import com.example.clientadmin.model.dto.OrderItemDto
import com.example.clientadmin.model.enumerator.OrderStatus


@Composable
fun OrderItem(orderDto: OrderDto, navHostController: NavHostController) {
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
                Text(text = orderDto.id.substring(0,6), style = style12)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "AMOUNT: ", style = style8)
                Text(text = "${orderDto.totalPrice}", color = colorResource(id = R.color.secondary), style = style12)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "STREET: ", style = style8)
            Text(text = "${orderDto.resilientInfos.street}, ${orderDto.resilientInfos.civicNumber}", color = colorResource(id = R.color.black), style = style12)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "STATUS: ", style = style8)
            Text(text = "${orderDto.status}",
                color = colorResource(
                    id = when(orderDto.status){
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
            Text(text = "${orderDto.date}", color = colorResource(id = R.color.black), style = style12)
        }

        OrderProductsList(orderItems = orderDto.items, navHostController = navHostController)
    }
}

@Composable
fun OrderProductsList(orderItems: List<OrderItemDto>, navHostController: NavHostController){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(orderItems) {
            orderItemDto ->
            key(orderItemDto.id) {
                ProductOrderItem(orderItemDto = orderItemDto){
                    navHostController.navigate("product/${orderItemDto.product.product.id}")
                }
            }
        }
    }
}

@Composable
fun ProductOrderItem(orderItemDto: OrderItemDto, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            bitmap = product.pic1,
//            contentDescription = null,
//            contentScale = ContentScale.Crop, //todo non so minimamente come fare
//            modifier = Modifier
//                .size(60.dp)
//                .clip(RoundedCornerShape(10.dp))
//        )
        Text(
            text = orderItemDto.product.product.name,
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(
            text = "QUANTITY: ${orderItemDto.quantity}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(
            text = "SIZE: ${orderItemDto.product.size}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
    }
}