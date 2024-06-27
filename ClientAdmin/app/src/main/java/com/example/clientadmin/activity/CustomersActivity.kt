package com.example.clientadmin.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.enumerator.FilterType
import com.example.clientadmin.viewmodels.CustomersViewModel
import com.example.clientadmin.viewmodels.formViewModel.FilterFormViewModel

@Composable
fun Users(navHostController: NavHostController, customersViewModel: CustomersViewModel, filterFormViewModel: FilterFormViewModel) {
    val customers by customersViewModel.customerSummaries.collectAsState()

    val page by customersViewModel.page.collectAsState()

    val (isOpenSheet, setBottomSheet) = remember { mutableStateOf(false) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { Title() }

        item { Search(name = stringResource(R.string.list_all_user)) { setBottomSheet(true) } }

        if(customers.isEmpty())
            item{
                Text(
                    text = stringResource(id = R.string.list_empty),
                    color = colorResource(id = R.color.black),
                    style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        else {
            items(customers) {
                customer ->
                key(customer.id) {
                    CustomerItem(customer = customer) { navHostController.navigate("user/${customer.id}") }
                }
            }

            item {
                if (page.number < page.totalPages)
                    Text(
                        text = stringResource(id = R.string.more),
                        color = colorResource(id = R.color.black50),
                        style = TextStyle(fontSize = 16.sp, letterSpacing = 5.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ){ customersViewModel.incrementPage() }
                    )
            }
        }
    }

    if (isOpenSheet)
        SearchByNameSheet(
            onDismissRequest = { setBottomSheet(false) },
            setBottomSheet = setBottomSheet,
            filterType = FilterType.CUSTOMERS,
            filterFormViewModel = filterFormViewModel
        ){ customersViewModel.setFilter(it) }
}
