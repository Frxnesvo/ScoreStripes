package com.example.clientuser.activity

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.ProductSummary

@Composable
fun Wishlist(myList: Any, sharedLists: List<Any>){ //todo vedere che oggetti passare
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            Text(
                text = "MY WISHLIST",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item{
            //WishListItem()
        }

        item {
            Text(
                text = "SHARED WITH ME",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(sharedLists){
            key {
                //WishListItem()
            }
        }
    }
}

@Composable
fun WishListItem(name: String, numProducts: Int, products: List<ProductSummary>, peoplesShares: List<Uri>){ //todo passare l'oggetto sopra
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.white), RoundedCornerShape(size = 30.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$numProducts Products",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.black50),
                    fontWeight = FontWeight.Normal
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy((-20).dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
                for (people in peoplesShares)
                    BoxIcon(
                        iconColor = colorResource(id = R.color.transparent),
                        content = people
                    ) { }
            }
        }

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductItem(product = products[0]) { }
            ProductItem(product = products[1]) { }
        }
    }
}