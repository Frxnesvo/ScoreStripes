package com.example.clientuser.activity

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.CustomerSummary
import com.example.clientuser.model.FilterBuilder
import com.example.clientuser.model.Personalization
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.Size
import com.example.clientuser.model.enumerator.WishListVisibility
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.LeagueViewModel
import com.example.clientuser.viewmodel.ProductViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedWithPanel(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    wishListViewModel: WishListViewModel
){
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val sharedToken = wishListViewModel.wishlistSharedToken
    val wishlistAccesses = wishListViewModel.myWishlistAccesses.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ){
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(wishlistAccesses.value){
                key(it) {
                    SwipeToDismissUserRow(guest = it) {
                        wishListViewModel.deleteWishlistAccess(it.id)
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .padding(10.dp)
        ){
            CustomButton(text = stringResource(id = R.string.share), background = R.color.secondary) {
                wishListViewModel.createSharedToken()
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Here is your invite token: ${sharedToken.value}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
                setBottomSheet(false)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissUserRow(guest: CustomerSummary, onRemove: () -> Unit){
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
        SharedWithUserRow(guest = guest)
    }
}

@Composable
fun SharedWithUserRow(guest: CustomerSummary){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = guest.username,
            style = MaterialTheme.typography.labelMedium
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .size(25.dp)
        ){
            Image(
                bitmap = guest.profilePic.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanelProducts(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    leagueViewModel: LeagueViewModel,
    productViewModel: ProductViewModel
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val filterBuilder = remember { FilterBuilder() }
    val leagues by leagueViewModel.leaguesNames.collectAsState(initial = emptyList())

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = "",
                text = stringResource(id = R.string.search_for_name),
                leadingIcon = Icons.Outlined.Search
            ) { filterBuilder.setName(it) }

            CustomComboBox(
                options = leagues,
                selectedOption = ""
            ) { filterBuilder.setLeague(it) }

            CustomComboBox(
                options = ProductCategory.entries,
                selectedOption = ""
            ) { filterBuilder.setCategory(it) }

            CustomComboBox(
                options = Size.entries,
                selectedOption = ""
            ) { filterBuilder.setSize(it) }

            CustomButton(
                text = stringResource(id = R.string.search),
                background = R.color.secondary
            ){
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        productViewModel.setFilter(filterBuilder.build())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAddress(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    customerViewModel: CustomerViewModel,
    onClick: (String) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val addresses by customerViewModel.addresses.collectAsState()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            addresses.forEach {address ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (sheetState.isVisible)
                                scope.launch {
                                    onClick(address.id)
                                    sheetState.hide()
                                    setBottomSheet(false)
                                }
                        },
                    horizontalArrangement = if (address.defaultAddress) Arrangement.SpaceBetween else Arrangement.Start
                ) {
                    Text(
                        text = "${address.street}, ${address.civicNumber}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    if (address.defaultAddress)
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = colorResource(id = R.color.secondary)
                        )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddress(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    customerViewModel: CustomerViewModel,
    addressFormViewModel: AddressFormViewModel
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val addressState by addressFormViewModel.addressState.collectAsState()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                value = addressState.street,
                isError = addressState.isStreetError,
                text = stringResource(id = R.string.street)
            ){ addressFormViewModel.updateStreet(it) }

            CustomTextField(
                value = addressState.civicNumber,
                isError = addressState.isCivicNumberError,
                text = stringResource(id = R.string.civic_number),
                keyboardType = KeyboardType.Number
            ){ addressFormViewModel.updateCivicNumber(it) }

            CustomTextField(
                value = addressState.state,
                isError = addressState.isStateError,
                text = stringResource(id = R.string.state)
            ){ addressFormViewModel.updateState(it) }

            CustomTextField(
                value = addressState.city,
                isError = addressState.isCityError,
                text = stringResource(id = R.string.city),
            ){ addressFormViewModel.updateCity(it) }

            CustomTextField(
                value = addressState.zipCode,
                isError = addressState.isZipCodeError,
                text = stringResource(id = R.string.zip_code),
                keyboardType = KeyboardType.Number
            ){ addressFormViewModel.updateZipCode(it) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = addressState.defaultAddress,
                    onCheckedChange = { addressFormViewModel.updateDefaultAddress(it) }
                )
                Text(text = stringResource(id = R.string.default_address))
            }



            CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
                if(sheetState.isVisible)
                    scope.launch {
                        customerViewModel.addAddress(
                            AddressCreateRequestDto(
                                street = addressState.street,
                                civicNumber = addressState.civicNumber,
                                state = addressState.state,
                                city = addressState.city,
                                zipCode = addressState.zipCode,
                                defaultAddress = addressState.defaultAddress
                            )
                        )
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

    val productState = productFormViewModel.productState.collectAsState()


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
                    isError = false,
                    text = stringResource(id = R.string.name_personalization)
                ) {
                    productFormViewModel.updatePersonalizationName(it)
                }
            }

            CustomTextField(
                value = "",
                isError = false,
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
                    text = productState.value.size!!.name,
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


            CustomButton(text = stringResource(id = R.string.add_to_cart), background = R.color.secondary) {
                if(sheetState.isVisible) {
                    val personalizationName = if (productState.value.name == "") null else productState.value.name
                    val personalizationNumber = if (productState.value.number == 0) null else productState.value.number
                    scope.launch {
                        cartViewModel.addProductToCart(
                            addToCartRequestDto = AddToCartRequestDto(
                                productId = product.id,
                                size = productState.value.size!!,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertWishlistTokenPanel(
    onDismissRequest: () -> Unit,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = "",
                text = stringResource(id = R.string.insert_token)
            ) {
                onValueChange(it)
            }

            CustomButton(
                text = stringResource(id = R.string.enter),
                background = R.color.secondary
            ) { onClick() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistVisibilityPanel(
    currentVisibility: WishListVisibility,
    onDismissRequest: () -> Unit,
    onValueChange: (WishListVisibility) -> Unit,
    onClick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomComboBox(
                options = WishListVisibility.entries,
                readOnly = true,
                selectedOption = currentVisibility.name
            ) {
                onValueChange(WishListVisibility.valueOf(it))
            }

            CustomButton(
                text = stringResource(id = R.string.change_visibility),
                background = R.color.secondary
            ) { onClick() }
        }
    }
}
