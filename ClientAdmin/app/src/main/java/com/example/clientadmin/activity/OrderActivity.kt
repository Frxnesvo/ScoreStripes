package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.OrderProduct


@Composable
fun OrderItem(order: Order, navHostController: NavHostController) {
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
                Text(text = "${order.id}", style = style12)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "AMOUNT: ", style = style8)
                Text(text = "${order.amount}", color = colorResource(id = R.color.secondary), style = style12)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "STREET: ", style = style8)
            Text(text = "${order.address.street}, ${order.address.houseNumber}", color = colorResource(id = R.color.black), style = style12)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "DATE: ", style = style8)
            Text(text = "${order.date}", color = colorResource(id = R.color.black), style = style12)
        }

        ProductOrdersList(order = order, navHostController = navHostController)

    }
}

@Composable
fun ProductOrdersList(order: Order, navHostController: NavHostController){
    LazyRow(
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(order.products) {
            product ->
            ProductOrderItem(orderProduct = product, navHostController = navHostController)
        }
    }
}

@Composable
fun ProductOrderItem(orderProduct: OrderProduct, navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .clickable { navHostController.navigate("product/${orderProduct.product.id}") },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.arsenal), // TODO prendere da product
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(text = orderProduct.product.team,
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
        )
        Text(text = "${orderProduct.product.category} ${orderProduct.product.season.yearStart}/${orderProduct.product.season.yearEnd}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(text = "QUANTITY: ${orderProduct.quantity}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
        Text(text = "SIZE: ${orderProduct.size}",
            color = colorResource(id = R.color.black50),
            style = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Light)
        )
    }
}