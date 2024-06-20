package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Order
import com.example.clientadmin.model.dto.AddressDto

@Composable
fun List(items: List<Any>, navHostController: NavHostController){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item { Back { navHostController.popBackStack() } }

        item {
            Text(
                text = when (items.first()) {
                    is Order -> stringResource(id = R.string.list_orders)
                    is AddressDto -> stringResource(id = R.string.list_addresses)
                    else -> { "" }
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (items.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.list_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        else
            items(items) {
                when (it) {
                    is Order -> OrderItem(order = it)
                    is AddressDto -> AddressItem(address = it)
                }
            }
    }
}