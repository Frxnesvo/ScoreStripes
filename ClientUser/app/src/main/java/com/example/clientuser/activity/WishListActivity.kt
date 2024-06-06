package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.dto.WishListDto
import com.example.clientuser.viewmodel.WishListViewModel

@Composable
fun Wishlist(
    myList: WishListDto,
    navHostController: NavHostController,
    wishListViewModel: WishListViewModel
){

    //TODO val myList = wishListViewModel.myWishList.collectAsState()
    val sharedLists = wishListViewModel.sharedWithMeWishlists.collectAsState(initial = emptyList())
    val publicLists = wishListViewModel.publicWishLists.collectAsState(initial = emptyList()) //TODO implementare il bottone

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.my_wishlist),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item{
            TODO("da sistemare")
            WishListItem(myList){ navHostController.navigate("productsList/${myList.id}") }
        }

        item {
            Text(
                text = stringResource(id = R.string.shared_with_me),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(sharedLists.value){
            key(it.id) {
                WishListItem(it){ navHostController.navigate("productsList/${it.id}") }
            }
        }
    }
}

@Composable
fun WishListItem(wishListDto: WishListDto, onClick: () -> Unit) {
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
                    text = wishListDto.ownerUsername,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${wishListDto.items.count()} Products",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.black50),
                    fontWeight = FontWeight.Normal
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy((-20).dp),
                verticalAlignment = Alignment.CenterVertically,
            ){
//                for (people in peoplesShares) //TODO vedere come ricevere l'immagine di quelli a cui è condivisa
//                    BoxIcon(
//                        iconColor = colorResource(id = R.color.transparent),
//                        content = people
//                    ) { }
            }
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductItem(productDto = wishListDto.items[0].product) { }
            ProductItem(productDto = wishListDto.items[1].product) { }
        }
    }
}