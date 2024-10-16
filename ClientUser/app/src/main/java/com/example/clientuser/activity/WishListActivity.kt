package com.example.clientuser.activity

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.LocalWishListViewModel
import com.example.clientuser.R
import com.example.clientuser.model.Wishlist
import com.example.clientuser.model.dto.WishlistVisibilityDto
import com.example.clientuser.model.enumerator.WishListVisibility
import com.example.clientuser.utils.ToastManager

@Composable
fun Wishlist(
    navHostController: NavHostController
){
    val wishListViewModel = LocalWishListViewModel.current

    val sharedLists = wishListViewModel.sharedWithMeWishlists
    val myWishlistAccesses by wishListViewModel.myWishlistAccesses.collectAsState()
    val myWishlist by wishListViewModel.myWishList.collectAsState()
    val myWishlistVisibility = remember { mutableStateOf(myWishlist.visibility) }
    val wishlistShareToken by wishListViewModel.wishlistSharedToken

    val (isOpenTokenSheet, setTokenBottomSheet) = remember { mutableStateOf(false) }
    val (isOpenVisibilitySheet, setVisibilityBottomSheet) = remember { mutableStateOf(false) }
    val (isShareWishlistOpenSheet, setShareBottomSheet) = remember { mutableStateOf(false) }

    val wishlistToAddToken = remember { mutableStateOf("") }

    val context = LocalContext.current


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
                painter = painterResource(id = R.drawable.collection)
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

                ) {

                    Text(
                        modifier = Modifier
                            .clickable {
                                setVisibilityBottomSheet(true)
                                setTokenBottomSheet(false)
                            },
                        text = myWishlist.visibility.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.secondary),
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
                            ) {

                                var count = 0
                                for (people in myWishlistAccesses) {
                                    if (count > 3) break
                                    count++
                                    BoxIcon(
                                        iconColor = colorResource(id = R.color.transparent),
                                        content = people.profilePic
                                    ) {
                                        setShareBottomSheet(true)
                                        setVisibilityBottomSheet(false)
                                        setTokenBottomSheet(false)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        item {
            if(myWishlist.items.isNotEmpty())
                WishListItem(myWishlist) { navHostController.navigate("myWishlist") }
            else
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyMedium
                )
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
        InsertWishlistTokenPanel(
            onDismissRequest = { setTokenBottomSheet(false) },
            onValueChange = {
                wishlistToAddToken.value = it
            },
            tokenToInsert = wishlistToAddToken.value,
            onClick = {
                wishListViewModel.validateShareToken(wishlistToAddToken.value)
                setTokenBottomSheet(false)
                wishlistToAddToken.value = ""
            }
        )
    }

    if(isOpenVisibilitySheet){
        WishlistVisibilityPanel(
            currentVisibility = myWishlistVisibility.value,
            onDismissRequest = {
                if(myWishlist.visibility != myWishlistVisibility.value) myWishlistVisibility.value = myWishlist.visibility
                setVisibilityBottomSheet(false)
            },
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
            onDismissRequest = { setShareBottomSheet(false) },
            wishListViewModel = wishListViewModel,
            onClick = {
                wishListViewModel.createSharedToken()
                setShareBottomSheet(false)
            }
        )
    }

    if(wishlistShareToken != ""){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, wishlistShareToken)
            type = "text/plain"
            wishListViewModel.clearWishlistShareToken()
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

}

@Composable
fun WishListItem(wishlist: Wishlist, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
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
                    text = "${wishlist.ownerUsername} wishlist",
                    style = MaterialTheme.typography.bodyLarge,
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
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProductItem(product = wishlist.items[0].product) { }
            if(wishlist.items.size > 1)
                ProductItem(product = wishlist.items[1].product) { }
        }
    }
}

