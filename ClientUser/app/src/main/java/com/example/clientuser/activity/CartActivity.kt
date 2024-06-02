package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientuser.R

@Preview
@Composable
fun Cart(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Title()
        }

        item {
            Text(
                text = stringResource(id = R.string.cart),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            CustomButton(
                text = stringResource(id = R.string.buy),
                background = R.color.secondary
            ) {
                //TODO logica dal viewModel
            }
        }

        items(3){
            ItemCart()
        }
    }
}

@Composable
fun ItemCart(){ //TODO vedere cosa passare
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.product), //todo prendere dal prodotto
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "ARSENAL", //todo prendere dal prodotto
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Homekit 23/24", //todo prendere dal prodotto
                    color = colorResource(id = R.color.black50),
                    style = MaterialTheme.typography.labelSmall
                )
                BoxIcon(
                    iconColor = colorResource(id = R.color.secondary),
                    size = 30.dp,
                    content = "XL"
                ) {}
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${119.99}€", //todo prendere dal prodotto
                    style = MaterialTheme.typography.labelMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.RemoveCircle,
                        contentDescription = null,
                        tint = colorResource(id = R.color.secondary),
                        modifier = Modifier.clickable { TODO() }
                    )
                    Text(
                        text = "1",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(15.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = colorResource(id = R.color.secondary),
                        modifier = Modifier.clickable { TODO() }
                    )
                }
            }
        }
    }
}