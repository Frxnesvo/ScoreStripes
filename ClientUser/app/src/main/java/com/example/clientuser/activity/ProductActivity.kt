package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.clientuser.R
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.model.enumerator.Size
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel


@Composable
fun ProductDetails(productDto: ProductDto, navHostController: NavHostController){
    val product = Product.fromDto(productDto)
    //TODO serve un form view model e anche il view model
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val productFormViewModel = ProductFormViewModel()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        IconButtonBar(
            imageVector = Icons.Outlined.Favorite, //todo fillarlo al click
            navHostController = navHostController
        ) {
            TODO()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = productDto.clubName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = productDto.clubName,
                style = MaterialTheme.typography.titleLarge
            )
        }

        //TODO fare il carosello per le immagini

        Image(
            painter = rememberAsyncImagePainter(product.pic1),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        for (size in Size.entries){

            BoxIcon(
                iconColor = colorResource(id = if (product.variants[size]!! > 0) R.color.secondary else R.color.black50),
                content = size
            ) {
                if (product.variants[size]!! > 0)
                    productFormViewModel.updateProductSize(size)
                    setBottomSheet(true)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

        }
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = product.brand,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "${product.gender}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "${product.productCategory}",
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (isOpenSheet)
        AddItemToCart(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            product = product,
            cartViewModel = CartViewModel(),
            productFormViewModel = productFormViewModel
        )
}