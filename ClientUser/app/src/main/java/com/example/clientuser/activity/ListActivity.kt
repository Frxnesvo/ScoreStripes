package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R
import com.example.clientuser.model.ExampleProduct
import com.example.clientuser.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun WishList(){
    val showSheet = remember { mutableStateOf(false) }

    //TODO da sostituire con lazyColumn
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 20.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ){
        Icon(
            background = colorResource(id = R.color.white),
            icon = Icons.Outlined.KeyboardArrowLeft,
            size = 40,
            iconColor = colorResource(id = R.color.red)
        ) {
        }

        ListDetailsRow(showSheet)
        ProductsList(products = ProductViewModel().getMostSelledProducts())
    }
    if(showSheet.value){
        ModalBottomSheet(
            onDismissRequest = {
                showSheet.value = false
            },
        ) {
            Guests()
        }
    }
}


@Composable
fun ListDetailsRow(showSheet: MutableState<Boolean>){
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "LISTA",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "3 prodotti",
                fontSize = 12.sp,
                color = colorResource(id = R.color.black50)
            )
        }


        Row (
            horizontalArrangement = Arrangement.spacedBy(-20.dp),
        ){
            repeat(3){
                ImageProfile(
                    size = 30,
                    onClick = {showSheet.value = true}
                )
            }
        }

    }
}
@Composable
fun ProductsList(products: List<ExampleProduct>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(products){
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProductImage(size = 120)
                ProductDetail(
                    team = it.team,
                    kit = it.homekit,
                    teamFont = 16,
                    kitFont = 12,
                    teamSpacing = 1,
                    kitSpacing = 0,
                    gap = 5,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun Guests(){
    //TODO fare lazy column
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            )
            .fillMaxWidth()
    ){
        repeat(2){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "UTENTE",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                ImageProfile(size = 30)
            }
        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red)),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "INVITA",
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}