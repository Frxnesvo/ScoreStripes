package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.Wishlist
import com.example.clientuser.viewmodel.WishListViewModel

@Composable
fun Wishlist(
    navHostController: NavHostController,
    wishListViewModel: WishListViewModel
){
    val sharedLists = wishListViewModel.sharedWithMeWishlists
    val myWishlist = wishListViewModel.myWishList
    val (isOpenTokenSheet, setTokenBottomSheet) = remember { mutableStateOf(false) }

    val wishlistToAddToken = StringBuilder("")

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

        item {
            BoxImage(
                boxTitle = stringResource(id = R.string.discover_wishlists),
                painter = painterResource(id = R.drawable.collection) //TODO trovare un'immagine più adatta
            ) { navHostController.navigate("discoverWishlist") }
        }

        item{
            BoxImage(
                boxTitle = stringResource(id = R.string.my_wishlist),
                painter = painterResource(id = R.drawable.collection) //TODO trovare un'immagine più adatta
            ){ navHostController.navigate("myWishlist") }
        }

        item {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = stringResource(id = R.string.shared_with_me),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                BoxIcon(
                    background = colorResource(id = R.color.secondary),
                    iconColor = colorResource(id = R.color.primary),
                    content = Icons.Outlined.Add
                ) {
                    setTokenBottomSheet(true)
                }
            }
        }

        items(sharedLists.value){
            key(it.id) {
                WishListItem(it){ navHostController.navigate("sharedWishlistProducts/${it.id}") }
            }
        }
    }

    if(isOpenTokenSheet){
        //TODO gestione degli errori
        InsertWishlistTokenPanel(
            onDismissRequest = { setTokenBottomSheet(false) },
            onValueChange = {
                wishlistToAddToken.setLength(0)
                wishlistToAddToken.append(it)
            },
            onClick = {
                wishListViewModel.validateShareToken(wishlistToAddToken.toString())
                setTokenBottomSheet(false)
                wishlistToAddToken.setLength(0)
            }
        )
    }
}

@Composable
fun WishListItem(wishlist: Wishlist, onClick: () -> Unit) {
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
                    text = wishlist.ownerUsername,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${wishlist.items.count()} Products",
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
            ProductItem(product = wishlist.items[0].product) { }
            ProductItem(product = wishlist.items[1].product) { }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(id = R.string.my_wishlist),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                modifier = Modifier
                    .clickable {
                        setBottomSheet(true)
                        //TODO logica view model
                   },
                text = stringResource(id = R.string.shared),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.secondary50),
            )
        }
    }

    if(isOpenSheet){
        WishlistVisibilityPanel(
            currentVisibility = TODO() ,
            onDismissRequest = { setBottomSheet(false) },
            onValueChange = { },
            onClick = {
                setBottomSheet(false)
            }
        )
    }

}