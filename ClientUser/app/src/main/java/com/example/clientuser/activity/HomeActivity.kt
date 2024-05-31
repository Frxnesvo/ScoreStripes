package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R
import com.example.clientuser.model.ExampleProduct
import com.example.clientuser.viewmodel.ProductViewModel

@Composable
fun Home(viewModel: ProductViewModel){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            )
    ){
        Title()
        Greetings()
        HomeButton()

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            ProductList(viewModel.getMostSelledProducts())
        }
    }
}


@Composable
fun Greetings(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        val textStyle = TextStyle(
            fontSize = 16.sp,
            letterSpacing = 3.sp
        )
        Row{
            Text(
                text = "CIAO, ",
                style = textStyle
            )

            Text(
                text = "FRANCESCO",
                color = colorResource(id = R.color.red),
                style = textStyle,
                fontWeight = FontWeight.Bold
            )
        }

        BoxIcon(
            background = colorResource(id = R.color.red),
            content = Icons.Outlined.Search,
            iconColor = colorResource(R.color.white),
        ){}
    }
}

@Composable
fun HomeButton(){
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.black50), RoundedCornerShape(30.dp))
            .clickable { /* TODO */ },
        contentAlignment = Alignment.BottomCenter
    ){
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "button image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
        )

        Row(
            modifier = Modifier
                .padding(10.dp)
        ){
            //ButtonText(text = "COLLEZIONE ", colorId = R.color.white)
            //ButtonText(text = "2024", colorId = R.color.red)
        }

    }
}

@Composable
fun ProductList(products: List<ExampleProduct>){
    Text(
        text = "I PIÙ VENDUTI",
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ){
        items(products) { product ->
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

            }

        }
    }
}