package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.R
import com.example.clientadmin.model.FilterBuilder
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.viewmodels.CustomerViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import com.example.clientadmin.viewmodels.ProductViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPanelCostumers(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    customerViewModel: CustomerViewModel
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val filterBuilder = remember { FilterBuilder() }

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = "",
                text = stringResource(id = R.string.search_for_username),
                leadingIcon = Icons.Outlined.Search
            ) { filterBuilder.setName(it) }

            CustomButton(
                text = stringResource(id = R.string.search),
                background = R.color.secondary
            ){
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        customerViewModel.setFilters(filterBuilder.build())
                    }
                }
            }
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

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = "",
                text = stringResource(id = R.string.search_for_username),
                leadingIcon = Icons.Outlined.Search
            ) { filterBuilder.setName(it) }

            CustomComboBox(
                options = leagueViewModel.leaguesNames,
                selectedOption = remember { mutableStateOf("") }
            ) { filterBuilder.setLeague(it) }

            CustomComboBox(
                options = flowOf(ProductCategory.entries),
                selectedOption = remember { mutableStateOf("") }
            ) { filterBuilder.setCategory(it) }

            CustomComboBox(
                options = flowOf(Size.entries),
                selectedOption = remember { mutableStateOf("") }
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