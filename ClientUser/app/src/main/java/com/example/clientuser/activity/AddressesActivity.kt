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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.clientuser.viewmodel.CustomerViewModel
import com.example.clientuser.viewmodel.formviewmodel.AddressFormViewModel

@Composable
fun Addresses(
    customerViewModel: CustomerViewModel,
    addressFormViewModel: AddressFormViewModel,
    navHostController: NavHostController
){
    val (isOpenSheetAdd, setBottomSheetAdd) = remember { mutableStateOf(false) }
    val (isOpenSheetSet, setBottomSheetSet) = remember { mutableStateOf(false) }

    val addressToSetDefault = remember { mutableStateOf("") }

    val addresses by customerViewModel.addresses.collectAsState()

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
                setBottomSheetAdd(true)
            }
        }

        item {
            Text(
                text = stringResource(id = R.string.addresses),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (addresses.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.nothing_to_show),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        else
            items(addresses){
                address ->
                key(address.id) {
                    AddressItem(addressDto = address){
                        setBottomSheetSet(true)
                        addressToSetDefault.value = address.id
                    }
                }
            }
    }

    if (isOpenSheetSet)
        SetDefaultAddress(
            onDismissRequest = { setBottomSheetSet(false) },
            setBottomSheet = setBottomSheetSet
        ) {
            if(addressToSetDefault.value.isNotEmpty())
                customerViewModel.setDefaultAddress(addressToSetDefault.value)
        }

    if (isOpenSheetAdd)
        AddAddress(
            onDismissRequest = { setBottomSheetAdd(false) },
            setBottomSheet = setBottomSheetAdd,
            addressFormViewModel = addressFormViewModel,
            customerViewModel = customerViewModel
        )
}