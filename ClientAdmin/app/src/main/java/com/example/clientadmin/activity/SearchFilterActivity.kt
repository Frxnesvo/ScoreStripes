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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientadmin.R
import com.example.clientadmin.model.enumerator.FilterType
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.viewmodels.formViewModel.FilterFormViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByNameSheet(
    onDismissRequest: () -> Unit,
    setBottomSheet: (Boolean) -> Unit,
    filterType: FilterType,
    filterFormViewModel: FilterFormViewModel,
    onSearch: (Map<String, String>?) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val filter by filterFormViewModel.filterState.collectAsState()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(
                value = filter.name,
                text = stringResource(id = if(filterType == FilterType.CUSTOMERS) R.string.search_for_username else R.string.search_for_name),
                leadingIcon = Icons.Outlined.Search
            ) { filterFormViewModel.updateName(it) }

            CustomButton(
                text = stringResource(id = R.string.search),
                background = R.color.secondary
            ){
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        onSearch(filterFormViewModel.build())
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
    leagues: List<String>,
    filterType: FilterType,
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
                text = stringResource(id = R.string.search_for_username),
                leadingIcon = Icons.Outlined.Search
            ) { filterFormViewModel.updateName(it) }

            CustomComboBox(
                options = leagues + listOf(""),
                text = stringResource(id = R.string.league),
                selectedOption = filter.league
            ) { filterFormViewModel.updateLeague(it) }

            if(filterType == FilterType.PRODUCTS)
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
                        onSearch(filterFormViewModel.build())
                    }
                }
            }
        }
    }
}