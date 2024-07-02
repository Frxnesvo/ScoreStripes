package com.example.clientuser.activity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import com.example.clientuser.LocalProductViewModel
import com.example.clientuser.LocalWishListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(product: Product){
    val pagerState = rememberPagerState(pageCount = { 2 })

    HorizontalPager(
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
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp)),
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            PageIndicator(page)
        }
    }
}

@Composable
fun PageIndicator(page: Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
    ){
        Box(
            modifier = Modifier
                .width(if (page == 0) 20.dp else 10.dp)
                .height(10.dp)
                .background(colorResource(id = if (page == 0) R.color.secondary else R.color.secondary20), RoundedCornerShape(5.dp))

        )
        Box(
            modifier = Modifier
                .width(if (page == 1) 20.dp else 10.dp)
                .height(10.dp)
                .background(colorResource(id = if (page == 1) R.color.secondary else R.color.secondary20), RoundedCornerShape(5.dp))
        )
    }
}

@Composable
fun ProductDetails(
    productId: String,
    navHostController: NavHostController,
    productFormViewModel: ProductFormViewModel
){
    val productViewModel = LocalProductViewModel.current
    val wishListViewModel = LocalWishListViewModel.current

    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val wishlist = wishListViewModel.myWishList
    val product by productViewModel.product.collectAsState()
    
    val showSnackBar = remember { mutableStateOf(false) }

    val inWishlist = remember { mutableStateOf(wishlist.value.items.any { it.product.id == productId}) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        IconButtonBar(
            imageVector = if (inWishlist.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            navHostController = navHostController
        ) {
            println(inWishlist.value)
            if(inWishlist.value){
                    wishListViewModel.deleteItem(product.id)
                    println("delete item from wishlist")
                    inWishlist.value = false
            }
            else {
                if(LogoutManager.instance.isLoggedIn.value) {
                    wishListViewModel.addItemToWishlist(AddToWishListRequestDto(product.id))
                    println("added item to wishlist")
                    inWishlist.value = true
                } else showSnackBar.value = true
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = product.club,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Carousel(product = product)

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
                text = "${product.price}€",
                style = textStyle,
                color = colorResource(id = R.color.secondary)
            )
        }


        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            for (size in Size.entries){
                val greaterThanZero = (product.variants[size] ?: 0) > 0
                BoxIcon(
                    background = colorResource(id = if (greaterThanZero) R.color.secondary else R.color.black50),
                    iconColor = colorResource(id = R.color.white),
                    content = size.name
                ) {
                    if (greaterThanZero) {
                        productFormViewModel.updateProductSize(size)
                        setBottomSheet(true)
                    }
                    else ToastManager.show("product out of stocks for this size")
                }
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "${product.gender}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "${product.productCategory}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }

    if (isOpenSheet)
        AddItemToCart(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            product = product,
            productFormViewModel = productFormViewModel
        )
    }

    if(showSnackBar.value) GoToLoginSnackBar(navController = navHostController) { showSnackBar.value = false }
    //todo Icona wishlist, va la recomposition, ma non cambia l'icona


}