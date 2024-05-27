package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.CustomerViewModel

@Composable
fun Users(navHostController: NavHostController, customerViewModel: CustomerViewModel) {
    val customers = customerViewModel.customerSummaries.value
    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { Title() }

        item { Search(name = stringResource(R.string.list_all_user)) { setBottomSheet(true) } }

        if(customers.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.list_all_user_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp)
                )
            }
        else {
            items(customers) {
                customer ->
                key(customer.id) {
                    UserItem(customer = customer) { navHostController.navigate("user/${customer.toQueryString()}") }
                }
            }

            item {
                TextButton(onClick = { customerViewModel.incrementAll() }) {
                    Text(
                        text = stringResource(id = R.string.more),
                        color = colorResource(id = R.color.white50),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 5.sp
                        )
                    )
                }
            }
        }
    }

    if (isOpenSheet)
        SearchPanelCostumers(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            customerViewModel = customerViewModel
        )
}
