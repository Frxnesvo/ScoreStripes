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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.Wishlist
import com.example.clientuser.model.dto.WishlistVisibilityDto
import com.example.clientuser.model.enumerator.WishListVisibility
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.viewmodel.WishListViewModel

@Composable
fun Wishlist(
    navHostController: NavHostController,
    wishListViewModel: WishListViewModel
){
    val sharedLists = wishListViewModel.sharedWithMeWishlists
    val myWishlistAccesses by wishListViewModel.myWishlistAccesses.collectAsState()
    val myWishlist by wishListViewModel.myWishList
    val myWishlistVisibility = remember { mutableStateOf(WishListVisibility.valueOf(myWishlist.visibility.name)) }

    val (isOpenTokenSheet, setTokenBottomSheet) = remember { mutableStateOf(false) }
    val (isOpenVisibilitySheet, setVisibilityBottomSheet) = remember { mutableStateOf(false) }
    val (isShareWishlistOpenSheet, setShareBottomSheet) = remember { mutableStateOf(false) }

    val wishlistToAddToken = StringBuilder("")

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {

        item {
            Title()
        }

        item {
            BoxImage(
                boxTitle = stringResource(id = R.string.discover_wishlists),
                painter = painterResource(id = R.drawable.collection) //TODO trovare un'immagine più adatta
            ) { navHostController.navigate("discoverWishlist") }
        }

        item {
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

                Row (
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {                                   //TODO non è la cosa migliore fare row dentro row

                    Text(
                        modifier = Modifier
                            .clickable {
                                setVisibilityBottomSheet(true)
                                setTokenBottomSheet(false)
                            },
                        text = myWishlist.visibility.name,        //TODO
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.secondary50),
                    )

                    if(myWishlist.visibility == WishListVisibility.SHARED){
                        if(myWishlistAccesses.isEmpty()){
                            BoxIcon(
                                iconColor = colorResource(id = R.color.primary),
                                background = colorResource(id = R.color.secondary),
                                content = Icons.Outlined.Share
                            ) {
                                setShareBottomSheet(true)
                                setVisibilityBottomSheet(false)
                                setTokenBottomSheet(false)
                            }
                        }
                        else {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-20).dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        setShareBottomSheet(true)
                                        setVisibilityBottomSheet(false)
                                        setTokenBottomSheet(false)
                                    }
                            ) {
                                for (people in myWishlistAccesses)
                                    BoxIcon(
                                        iconColor = colorResource(id = R.color.transparent),
                                        content = people.profilePic
                                    ) { }
                            }
                        }
                    }
                }
            }
        }

        if(myWishlist.items.isNotEmpty()) {
            item {
                WishListItem(myWishlist) { navHostController.navigate("sharedWishlistProducts/${myWishlist.id}") }
            }
        }

        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
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
                    setVisibilityBottomSheet(false)
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

    if(isOpenVisibilitySheet){
        WishlistVisibilityPanel(
            currentVisibility = myWishlistVisibility.value,
            onDismissRequest = { setVisibilityBottomSheet(false) },
            onClick = {
                if(myWishlistVisibility.value != myWishlist.visibility)
                    wishListViewModel.changeWishlistVisibility(WishlistVisibilityDto(myWishlistVisibility.value))
                else ToastManager.show("wishlist visibility is already ${myWishlistVisibility.value}")
                setVisibilityBottomSheet(false)
            },
            onValueChange = {
                myWishlistVisibility.value = WishListVisibility.valueOf(it)
            },
        )
    }

    if(isShareWishlistOpenSheet){
        SharedWithPanel(
            onDismissRequest = { setShareBottomSheet(false) },  //TODO logica viewn model
            setBottomSheet = setShareBottomSheet,
            wishListViewModel = wishListViewModel
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
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductItem(product = wishlist.items[0].product) { }
            if(wishlist.items.size > 1)
                ProductItem(product = wishlist.items[1].product) { }
        }
    }
}

