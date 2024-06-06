package com.example.clientuser.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.viewmodel.AddressViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel

@Composable
fun Addresses(
    addressViewModel: AddressViewModel,
    addressFormViewModel: AddressFormViewModel,
    navHostController: NavHostController
){
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

    val addresses = addressViewModel.addresses

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        item {
            IconButtonBar(
                imageVector = Icons.Outlined.Add,
                navHostController = navHostController
            ) {
                setBottomSheet(true)
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.addresses),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (addresses.value.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        else
            items(addresses.value){
                address ->
                key(address.id) {
                    AddressItem(addressDto = address)
                }
            }
    }

    if (isOpenSheet)
        AddAddress(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            addressFormViewModel = addressFormViewModel,
            addressViewModel = addressViewModel
        )
}