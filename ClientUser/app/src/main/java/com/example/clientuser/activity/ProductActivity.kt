package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.authentication.LogoutManager
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.AddToWishListRequestDto
import com.example.clientuser.model.enumerator.Size
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@Composable
fun Carousel(product: Product){
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = 2,
        state = pagerState
    ) { page ->
        val image = when(page){
            1 -> product.pic1
            else -> product.pic2
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .size(120.dp),
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(10.dp) )

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = colorResource(id = R.color.secondary),
                inactiveColor = colorResource(id = R.color.secondary20),
                spacing = 8.dp,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ProductDetails(
    productId: String,
    wishListViewModel: WishListViewModel,
    navHostController: NavHostController,
    productFormViewModel: ProductFormViewModel,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel
){
    //TODO serve il view model e capire come gestire il like
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val wishlist = wishListViewModel.myWishList
    val inWishlist = remember { mutableStateOf(wishlist.value.items.any { it.product.id == productId}) }
    val product = productViewModel.product.collectAsState()

    val showSnackBar = remember { mutableStateOf(false) }

    var favouriteBackground = colorResource(id = R.color.primary)
    var favouriteIconColor = colorResource(id = R.color.secondary)
    var onFavouriteClick = {
        if(LogoutManager.instance.isLoggedIn.value) {
            wishListViewModel.deleteItem(product.value!!.id)
            inWishlist.value = false
        }else showSnackBar.value = true
    }

    if(!inWishlist.value){
        favouriteBackground = colorResource(id = R.color.secondary)
        favouriteIconColor = colorResource(id = R.color.primary)
        onFavouriteClick = {
            if(LogoutManager.instance.isLoggedIn.value) {
                wishListViewModel.addItemToWishlist(AddToWishListRequestDto(product.value!!.id))
                inWishlist.value = true
            }else showSnackBar.value = true
        }
    }

    //TODO capire perchè all'inizio inWishlist è sempre true e non cambia il colore dell'icona

    if(product.value != null){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            //TODO vedere perchè non cambia le icone
            IconButtonBar(
                background = favouriteBackground,
                iconColor = favouriteIconColor,
                imageVector = Icons.Filled.Favorite,
                navHostController = navHostController
            ){ onFavouriteClick() }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = product.value!!.club,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = product.value!!.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Carousel(product = product.value!!)

            Row {
                val textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "PREZZO: ",
                    style = textStyle
                )

                Text(
                    text = "${product.value!!.price}€",
                    style = textStyle,
                    color = colorResource(id = R.color.secondary)
                )
            }


            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                for (size in Size.entries){
                    BoxIcon(
                        iconColor = colorResource(id = if (product.value!!.variants[size]!! > 0) R.color.secondary else R.color.black50),
                        content = size.name
                    ) {
                        if(LogoutManager.instance.isLoggedIn.value) {
                            if (product.value!!.variants[size]!! > 0) {
                                productFormViewModel.updateProductSize(size)
                                setBottomSheet(true)
                            } else ToastManager.show("product out of stocks for this size")
                        }
                        else showSnackBar.value = true
                    }
                }
            }


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = product.value!!.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = product.value!!.brand,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${product.value!!.gender}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${product.value!!.productCategory}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

        if (isOpenSheet)
            AddItemToCart(
                onDismissRequest = { setBottomSheet(false) },
                setBottomSheet = setBottomSheet,
                product = product.value!!,
                cartViewModel = cartViewModel,
                productFormViewModel = productFormViewModel
            )
    }

    if(showSnackBar.value) GoToLoginSnackBar(navController = navHostController) { showSnackBar.value = false }
    //todo Icona wishlist, va la recomposition, ma non cambia l'icona


}