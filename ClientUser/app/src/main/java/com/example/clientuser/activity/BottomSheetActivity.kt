package com.example.clientuser.activity

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.CustomerSummary
import com.example.clientuser.model.Personalization
import com.example.clientuser.model.Product
import com.example.clientuser.model.dto.AddToCartRequestDto
import com.example.clientuser.model.dto.AddressCreateRequestDto
import com.example.clientuser.model.dto.AddressDto
import com.example.clientuser.model.enumerator.ProductCategory
import com.example.clientuser.model.enumerator.WishListVisibility
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.CartViewModel
import com.example.clientuser.viewmodel.WishListViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.FilterFormViewModel
import com.example.clientuser.viewmodel.formviewmodel.ProductFormViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedWithPanel(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    wishListViewModel: WishListViewModel,
    onClick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

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
                    SwipeToDismissItem(
                        content = { SharedWithUserRow(guest = it) }
                    ) {
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
                onClick()

            }
        }
    }
}

@Composable
fun SharedWithUserRow(guest: CustomerSummary){
    Row(
        modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
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
    val isError = remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = colorResource(id = R.color.white),
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                value = addressState.street,
                isError = addressState.isStreetError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.street)
            ){ addressFormViewModel.updateStreet(it) }

            CustomTextField(
                value = addressState.civicNumber,
                isError = addressState.isCivicNumberError,
                errorMessage = stringResource(id = R.string.civic_number_error),
                text = stringResource(id = R.string.civic_number),
                keyboardType = KeyboardType.Number
            ){ addressFormViewModel.updateCivicNumber(it) }

            CustomTextField(
                value = addressState.state,
                isError = addressState.isStateError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.state)
            ){ addressFormViewModel.updateState(it) }

            CustomTextField(
                value = addressState.city,
                isError = addressState.isCityError,
                errorMessage = stringResource(id = R.string.null_error),
                text = stringResource(id = R.string.city),
            ){ addressFormViewModel.updateCity(it) }

            CustomTextField(
                value = addressState.zipCode,
                isError = addressState.isZipCodeError,
                errorMessage = stringResource(id = R.string.zip_code_error),
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

            isError.value = addressState.isStateError || addressState.isStreetError || addressState.isCityError || addressState.isZipCodeError || addressState.isCivicNumberError


            CustomButton(
                enabled = !isError.value,
                text = stringResource(id = R.string.create),
                background = if(isError.value) R.color.black50 else R.color.secondary
            ) {
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
fun RemoveAddress(
    onDismissRequest: () -> Unit,
    address: AddressDto,
    onClick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = colorResource(id = R.color.white),
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                value = address.street,
                readOnly = true,
                text = stringResource(id = R.string.street)
            ){}

            CustomTextField(
                value = address.civicNumber,
                readOnly = true,
                text = stringResource(id = R.string.civic_number),
            ){}

            CustomTextField(
                value = address.state,
                readOnly = true,
                text = stringResource(id = R.string.state)
            ){}

            CustomTextField(
                value = address.city,
                readOnly = true,
                text = stringResource(id = R.string.city),
            ){}

            CustomTextField(
                value = address.zipCode,
                readOnly = true,
                text = stringResource(id = R.string.zip_code),
            ){}

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = address.defaultAddress,
                    enabled = false,
                    onCheckedChange = {}
                )
                Text(text = stringResource(id = R.string.default_address))
            }

            CustomButton(
                text = stringResource(id = R.string.delete),
                textColor = colorResource(id = R.color.secondary50),
                background = R.color.black50
            ) {
                onClick()
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
    tokenToInsert: String,
    onDismissRequest: () -> Unit,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = { onDismissRequest() }, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = tokenToInsert,
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
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    currentVisibility: WishListVisibility,
    onValueChange: (String) -> Unit
){
    val sheetState = rememberModalBottomSheetState()


    ModalBottomSheet(onDismissRequest = { onDismissRequest() }, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomComboBox(
                options = WishListVisibility.entries,
                readOnly = true,
                text = stringResource(id = R.string.visibility),
                selectedOption = currentVisibility.name
            ) {
                onValueChange(it)
            }

            CustomButton(
                text = stringResource(id = R.string.change_visibility),
                background = R.color.secondary
            ) {
                onClick()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanelProducts(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    leagues: List<String>,
    filterFormViewModel: FilterFormViewModel,
    onSearch: (Map<String, String>?) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val filter by filterFormViewModel.filterState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = colorResource(id = R.color.white),
    ) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = filter.name,
                text = stringResource(id = R.string.search_for_name),
                leadingIcon = Icons.Outlined.Search
            ) { filterFormViewModel.updateName(it) }

            CustomComboBox(
                options = leagues + listOf(""),
                text = stringResource(id = R.string.league),
                selectedOption = filter.league
            ) { filterFormViewModel.updateLeague(it) }

            CustomComboBox(
                options = ProductCategory.entries + listOf(""),
                text = stringResource(id = R.string.category),
                selectedOption = filter.category
            ) { filterFormViewModel.updateCategory(it) }

            CustomButton(
                text = stringResource(id = R.string.search),
                background = R.color.secondary
            ){
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        onSearch(filterFormViewModel.buildForItems())
                    }
                }
            }
        }
    }
}