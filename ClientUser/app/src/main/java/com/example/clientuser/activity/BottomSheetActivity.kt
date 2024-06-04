package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.Personalization
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedWithPanel(
onDismissRequest: () -> Unit, //TODO da aggiungere il view model
setBottomSheet: (Boolean) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(3){
                key(it) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "PERSON $it",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            painter = painterResource(id = R.drawable.profilo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }

            item {
                CustomButton(text = stringResource(id = R.string.share), background = R.color.secondary) {
                    if(sheetState.isVisible)
                        scope.launch {
                            TODO("logica view model")
                            sheetState.hide()
                            setBottomSheet(false)
                        }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddress(
    onDismissRequest: () -> Unit, //TODO aggiungere view model e form view model
    setBottomSheet: (Boolean) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                value = TODO(),
                text = stringResource(id = R.string.street)
            ){ TODO("logica form view model") }

            CustomTextField(
                value = TODO(),
                text = stringResource(id = R.string.civic_number),
                keyboardType = KeyboardType.Number
            ){ TODO("logica form view model") }

            CustomTextField(
                value = TODO(),
                text = stringResource(id = R.string.state)
            ){ TODO("logica form view model") }

            CustomTextField(
                value = TODO(),
                text = stringResource(id = R.string.city),
            ){ TODO("logica form view model") }

            CustomTextField(
                value = TODO(),
                text = stringResource(id = R.string.zip_code),
                keyboardType = KeyboardType.Number
            ){ TODO("logica view model") }

            CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
                if(sheetState.isVisible)
                    scope.launch {
                        TODO("logica view model")
                        sheetState.hide()
                        setBottomSheet(false)
                    }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemToCart(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    product: Product,
    cartViewModel: CartViewModel,
    productFormViewModel: ProductFormViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()


    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            val isProductJersey = product.productCategory == ProductCategory.JERSEY

            if(isProductJersey) {
                CustomTextField(
                    value = "",
                    text = stringResource(id = R.string.name_personalization)
                ) {
                    productFormViewModel.updatePersonalizationName(it)
                }
            }

            CustomTextField(
                value = "",
                text = stringResource(id = if(isProductJersey) R.string.number_jersey_personalization else R.string.number_shorts_personalization ),
                keyboardType = KeyboardType.Number
            ) {
                productFormViewModel.updatePersonalizationNumber(if(it != "")it.toInt() else 0)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.size_mbs),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = productFormViewModel.productState.collectAsState().value.size!!.name,
                    color = colorResource(id = R.color.secondary),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.price_mbs),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "${product.price + productFormViewModel.calculatePersonalizationPrice()}€",
                    color = colorResource(id = R.color.secondary),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            val productState = productFormViewModel.productState.collectAsState().value
            CustomButton(text = stringResource(id = R.string.add_to_cart), background = R.color.secondary) {
                if(sheetState.isVisible) {
                    val personalizationName = if (productState.name == "") null else productState.name
                    val personalizationNumber = if (productState.number == 0) null else productState.number
                    scope.launch {
                        cartViewModel.addProductToCart(
                            addToCartRequestDto = AddToCartRequestDto(
                                productId = product.id,
                                size = productState.size!!,
                                category = product.productCategory,
                                personalization = Personalization(
                                    personalizationName,
                                    personalizationNumber
                                )
                            )
                        )
                        sheetState.hide()
                        setBottomSheet(false)
                    }
                }
            }
        }
    }
}