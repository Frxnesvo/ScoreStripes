package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Product
import com.example.clientadmin.model.Quantity
import com.example.clientadmin.model.Season
import com.example.clientadmin.model.Enum.Type
import java.time.Year

@Composable
fun Home(selectedScreen: MutableState<Screen>, navHostController: NavHostController) {
    val numNewOrders = 200
    val numProductOutOfStoke = 120
    val numNewUser = 88

    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
    ) {
        Title()

        Stats(
            numNewOrders = numNewOrders,
            numProductOutOfStoke = numProductOutOfStoke,
            numNewUser = numNewUser
        )

        BoxImage(boxTitle = "UTENTI", painterResource(id = R.drawable.match)){
            selectedScreen.value = Screen.USERS
            navHostController.navigate("users")
        }

        BoxImage(boxTitle = "PRODOTTI", painterResource(id = R.drawable.psg)){
            selectedScreen.value = Screen.PRODUCTS
            navHostController.navigate("products")
        }

        /*TODO - get dei prodotti: 1. più venduti 2. meno venduti*/


        val l : MutableList<Product> = mutableListOf()
        repeat(5){
            l.add(
                Product(
                    1,
                    "arsenal",
                    "Premier League",
                    Season(Year.now(), Year.now().plusYears(1)),
                    Type.MAN, Category.JERSEY,
                    "maglia bella",
                    null,
                    null,
                    15.00,
                    false,
                    Quantity()
                ))
        }

        Section("I PIÙ VENDUTI", l)
        Section("I MENO VENDUTI", l)

        /*TODO -  da vedere se aggiungere qualche altra sezione alla home*/
    }
}

@Composable
fun Stats(numNewOrders: Int, numProductOutOfStoke: Int, numNewUser: Int){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(id = R.color.white), shape = RoundedCornerShape(30.dp))
            .padding(10.dp))
    {
        Stat(text = "new orders", value = numNewOrders)

        Stat(text = "out of stoke", value = numProductOutOfStoke)

        Stat(text = "new users", value = numNewUser)
    }
}

@Composable
fun Stat(text: String, value: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = text, style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Light))
        Text(text = "$value", color = Color.Red, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp))
    }
}

@Composable
fun Section(nameSection: String, products: List<Product>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth())
    {
        Text(
            text = nameSection,
            color = Color.Black,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(700),
                letterSpacing = 5.sp
            )
        )

        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(products){
                ProductItemRow(it) {  }
            }
        }
    }
}