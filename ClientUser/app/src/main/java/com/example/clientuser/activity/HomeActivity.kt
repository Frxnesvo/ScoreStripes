package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R
import com.example.clientuser.model.ProductSummary
import com.example.clientuser.model.dto.ClubDto
import com.example.clientuser.model.dto.LeagueDto
import com.example.clientuser.model.dto.ProductDto
import com.example.clientuser.viewmodel.HomeViewModel
import com.example.clientuser.viewmodel.ProductViewModel

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController
){
    val clubs = homeViewModel.clubs.collectAsState(initial = emptyList())

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ){
        Title()

        Greetings(
            name = " " + "CIRO"
        ){}

        BoxImage(
            height = 300.dp,
            boxTitle = stringResource(id = R.string.discover),
            painter = painterResource(id = R.drawable.home)
        ) {  }

        Section(
            name = stringResource(id = R.string.best_seller),
            items = listOf(), //TODO get dei più venduti
            navHostController = navHostController
        )

        Section(
            name = stringResource(id = R.string.buy_by_club),
            items = clubs.value,
            navHostController = navHostController
        )

        Section(
            name = stringResource(id = R.string.buy_by_league),
            items = listOf(), //TODO get delle leghe
            navHostController = navHostController
        )
    }
}


@Composable
fun Greetings(
    name: String,
    onClickSearch: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row{
            Text(
                text = stringResource(id = R.string.greetings),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = name,
                color = colorResource(id = R.color.secondary),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        BoxIcon(
            background = colorResource(id = R.color.secondary),
            content = Icons.Outlined.Search,
            iconColor = colorResource(R.color.white),
            onclick = onClickSearch
        )
    }
}

@Composable
fun Section(
    name: String,
    items: List<Any>,
    navHostController: NavHostController
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ){
            if (items.isEmpty())
                item {
                    Text(
                        text = stringResource(id = R.string.nothing_to_show),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            else
                items(items){
                    when (it) {
                        is ProductDto -> key(it.id) {
                            ProductItem(it){ navHostController.navigate("product/${it.id}") }
                        }
                        is ClubDto -> key(it.id) {
                            ClubItem(it){ TODO("navHostController.navigate()")}
                        }
                        is LeagueDto -> key(it.id) {
                            LeagueItem(it){ TODO("navHostController.navigate()") }
                        }
                    }
                }
        }
    }
}