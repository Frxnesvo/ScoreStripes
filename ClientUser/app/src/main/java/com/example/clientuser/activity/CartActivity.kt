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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.CartItem
import com.example.clientuser.model.dto.OrderInfoDto
import com.example.clientuser.model.dto.UpdateCartItemDto
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.OrderViewModel

@Composable
fun Cart(
    orderViewModel: OrderViewModel,
    customerViewModel: CustomerViewModel,
    navHostController: NavHostController,
    cartViewModel: CartViewModel
){
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val selectedAddress = remember { mutableStateOf("") }
    val showWebView = remember { mutableStateOf(false) }

    val myCart = cartViewModel.cart.collectAsState()

    println("CART: $myCart")

    if (showWebView.value) {
        orderViewModel
            .createCartOrder(OrderInfoDto(selectedAddress.value))
            .collectAsState(initial = mapOf()).value["url"]?.let {
            url -> WebViewScreen(url, navHostController) {
                showWebView.value = false
            }
        }
    } else {
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
                    text = stringResource(id = R.string.go_to_payment),
                    background = R.color.secondary
                ) { setBottomSheet(true) }
            }

            items(myCart.value.values.toList()){
                key(it.id) {
                    ItemCart(
                        cartItem = it,
                        cartViewModel = cartViewModel)
                }
            }
            items(myCart.value.values.toList()){
                key(it.id) {
                    SwipeToDismissItem(
                        item = it,
                        cartViewModel = cartViewModel
                    ) {
                        cartViewModel.deleteItem(itemId = it.id)
                    }
                }
            }
        }
    }

    if(isOpenSheet)
        ChooseAddress(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            customerViewModel = customerViewModel
        ) {
            selectedAddress.value = it
            if (selectedAddress.value != "")
                showWebView.value = true
        }
}

@Composable
fun ItemCart(cartItem: CartItem, cartViewModel: CartViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            bitmap = cartItem.productWithVariant.product.pic.asImageBitmap(),
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
                    text = cartItem.productWithVariant.product.club,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = cartItem.productWithVariant.product.name,
                    color = colorResource(id = R.color.black50),
                    style = MaterialTheme.typography.labelSmall
                )
                BoxIcon(
                    iconColor = colorResource(id = R.color.secondary),
                    size = 30.dp,
                    content = cartItem.productWithVariant.size.name
                ) {}
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${cartItem.price}€",
                    style = MaterialTheme.typography.labelMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = null,
                        tint = colorResource(id = R.color.secondary),
                        modifier = Modifier.clickable {
                            cartViewModel.updateItemCartQuantity(
                                itemId = cartItem.id,
                                updateCartItemDto = UpdateCartItemDto(
                                    quantity = cartItem.quantity - 1
                                )
                            )
                        }
                    )
                    Text(
                        text = "${cartItem.quantity}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(15.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = colorResource(id = R.color.secondary),
                        modifier = Modifier.clickable {
                            cartViewModel.updateItemCartQuantity(
                                itemId = cartItem.id,
                                updateCartItemDto = UpdateCartItemDto(
                                    quantity = cartItem.quantity + 1
                                ),
                            )
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissItem(item: CartItem, cartViewModel: CartViewModel, onRemove : () -> Unit) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if(state == SwipeToDismissBoxValue.EndToStart){
                onRemove()
                true
            }
            else false
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            if(dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart)
                SwipeToDismissDeleteRow()
        }
    ) {
        ItemCart(cartItem = item, cartViewModel = cartViewModel )
    }
}
