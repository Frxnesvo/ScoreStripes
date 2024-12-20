package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientuser.LocalCartViewModel
import com.example.clientuser.R
import com.example.clientuser.model.CartItem
import com.example.clientuser.model.dto.OrderInfoDto
import com.example.clientuser.model.dto.UpdateCartItemDto
import com.example.clientuser.utils.ToastManager
import com.example.clientuser.viewmodel.OrderViewModel

@Composable
fun Cart( navHostController: NavHostController ){
    val orderViewModel = OrderViewModel()
    val cartViewModel = LocalCartViewModel.current

    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }
    val selectedAddress = remember { mutableStateOf("") }

    val showWebView = remember { mutableStateOf(false) }

    val webViewLink by orderViewModel.createdOrderWebViewLink

    val myCart = cartViewModel.cart.collectAsState()

    if(showWebView.value && webViewLink.isNotEmpty()){
        WebViewScreen(
            orderViewModel = orderViewModel,
            paymentUrl = webViewLink,
            navController = navHostController
        ) {
            showWebView.value = false
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item { Title() }

            item {
                Text(
                    text = stringResource(id = R.string.cart),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            if(myCart.value.isNotEmpty()){
                item {
                    CustomButton(
                        text = stringResource(id = R.string.go_to_payment),
                        background = R.color.secondary
                    ) {
                        setBottomSheet(true)
                    }
                }
            }
            else {
                item {
                    Text(
                        text = stringResource(id = R.string.cart_empty),
                        style = MaterialTheme.typography.labelMedium,
                        color = colorResource(id = R.color.black50),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            items(myCart.value.values.toList()){
                key(it.id) {
                    SwipeToDismissItem(
                        content = { ItemCart(cartItem = it) },
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
        ) {
            selectedAddress.value = it
            if (selectedAddress.value != "") {
                if(orderViewModel.createCartOrder(OrderInfoDto(selectedAddress.value))) {
                    cartViewModel.clearCart()
                    showWebView.value = true
                }else ToastManager.show("Error crating order")
            }
        }
}

@Composable
fun ItemCart(cartItem: CartItem) {
    val cartViewModel = LocalCartViewModel.current

    val item = remember { mutableStateOf(cartItem) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Image(
            bitmap = item.value.productWithVariant.product.pic.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
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
                    text = item.value.productWithVariant.product.club,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = item.value.productWithVariant.product.name,
                    color = colorResource(id = R.color.black50),
                    style = MaterialTheme.typography.labelSmall
                )
                BoxIcon(
                    iconColor = colorResource(id = R.color.secondary),
                    content = item.value.productWithVariant.size.name
                ) {}
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${item.value.price}€",
                    style = MaterialTheme.typography.labelMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.secondary),
                                RoundedCornerShape(30.dp)
                            )
                            .clickable {
                                if (item.value.quantity > 1) {
                                    cartViewModel.updateItemCartQuantity(
                                        itemId = item.value.id,
                                        updateCartItemDto = UpdateCartItemDto(
                                            quantity = item.value.quantity - 1
                                        )
                                    )
                                    item.value = item.value.copy(quantity = item.value.quantity - 1)
                                } else ToastManager.show("Quantity cannot be 0")
                            }
                            .size(20.dp),
                    ){
                        Text(
                            textAlign = TextAlign.Center ,
                            text = "-",
                            style = TextStyle(
                                color = colorResource(id = R.color.primary),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }


                    Text(
                        text = "${item.value.quantity}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.width(15.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = null,
                        tint = colorResource(id = R.color.secondary),
                        modifier = Modifier.clickable {
                            if(item.value.quantity < 99) {
                                cartViewModel.updateItemCartQuantity(
                                    itemId = item.value.id,
                                    updateCartItemDto = UpdateCartItemDto(
                                        quantity = item.value.quantity + 1
                                    ),
                                )
                                item.value = item.value.copy(quantity = item.value.quantity + 1)
                            }
                            else ToastManager.show("Quantity cannot be exceed 99")
                        }
                    )
                }
            }
        }
    }
}




