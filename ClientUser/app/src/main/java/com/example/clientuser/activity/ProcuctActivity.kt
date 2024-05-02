package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.clientuser.model.Enum.Size



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SingleProduct(){
    val selectedSize = remember { mutableStateOf(Size.M) }  //TODO da prendere come parametro la traglia

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 20.dp)
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            ),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            //TODO CAMBIARE ICONA "<"
            Icon(background = colorResource(id = R.color.white),
                icon = Icons.Outlined.KeyboardArrowLeft,
                size = 40,
                iconColor = colorResource(id = R.color.red),
                onclick = {}
            )

            Icon(background = colorResource(id = R.color.white),
                icon = Icons.Outlined.FavoriteBorder,
                size = 40,
                iconColor = colorResource(id = R.color.red),
                onclick = {}
            )
        }

        ProductDetail(
            team = "ARSENAL",
            kit = "Homekit 23/24",
            teamFont = 20,
            kitFont = 16,
            teamSpacing = 1,
            kitSpacing = 0,
            gap = 5,
            fontWeight = FontWeight.Normal
        )

        Image(
            painter = painterResource(id = R.drawable.product),
            contentDescription = "product",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(390.dp)         //TODO va bene fissata?
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Circle(colorResource(id = R.color.red), 20)     //active circle
            Circle(colorResource(id = R.color.red20), 10)
        }

        //usata per visualizare il bottomSheet
        val showSheet = remember { mutableStateOf(false) }

        PriceRow()
        SizeRow(selectedSize, showSheet)

        Description()

        if(showSheet.value){
            ModalBottomSheet(
                onDismissRequest = {
                    showSheet.value = false
                },
            ) {
                SizeDetails(size = selectedSize.value.toString())
            }
        }
    }
}

@Composable
fun Circle(background: Color, width: Int){
    Box(
        modifier = Modifier
            .background(background, RoundedCornerShape(5.dp))
            .width(width.dp)
            .height(10.dp)
    )
}

@Composable
fun SizeRow(selectedSize: MutableState<Size>, showSheet: MutableState<Boolean>){
    val red: Color = colorResource(id = R.color.red)
    val white: Color = colorResource(id = R.color.white)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ){


        SizeIcon(
            letter = "XS",
            size = 40,
            background = if(selectedSize.value == Size.XS) red else white,
            sizeColor = if(selectedSize.value == Size.XS) white else red,
            font = 16,
            onClick = {
                showSheet.value = true
                selectedSize.value = Size.XS
            }
        )

        SizeIcon(
            letter = "S",
            size = 40,
            background = if(selectedSize.value == Size.S) red else white,
            sizeColor = if(selectedSize.value == Size.S) white else red,
            font = 16,
            onClick = {
                showSheet.value = true
                selectedSize.value = Size.S
            }
        )

        SizeIcon(
            letter ="M",
            size = 40,
            background = if(selectedSize.value == Size.M) red else white,
            sizeColor = if(selectedSize.value == Size.M) white else red,
            font = 16,
            onClick = {
                showSheet.value = true
                selectedSize.value = Size.M
            }
        )

        SizeIcon(
            letter ="L",
            size = 40,
            background = if(selectedSize.value == Size.L) red else white,
            sizeColor = if(selectedSize.value == Size.L) white else red,
            font = 16,
            onClick = {
                showSheet.value = true
                selectedSize.value = Size.L
            }
        )

        SizeIcon(
            letter ="XL",
            size = 40,
            background = if(selectedSize.value == Size.XL) red else white,
            sizeColor = if(selectedSize.value == Size.XL) white else red,
            font = 16,
            onClick = {
                showSheet.value = true
                selectedSize.value = Size.XL
            }
        )
    }
}

@Composable
fun PriceRow(){
    Row{
        val textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 1.sp
        )
        Text(
            text = "PREZZO: ",
            style = textStyle
        )

        Text(
            text = "89.99€",
            color = colorResource(id = R.color.red),
            style = textStyle
        )

    }
}

@Composable
fun Description(){
    Text(
        text = "jhgfawvjscyouagsrcvpiyaergvipauhgvuaihrviuahpuivdhgbaliyrsgvbiauyergviqyagvbdciya" +
                "sfgjv<bahsfbvkhalsbfvklhzbxfvhklabkflhbakljhladhsfjhjahdfljhas",
        color = colorResource(id = R.color.black50),
        fontSize = 16.sp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ){
        Text(
            text = "UOMO",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}

@Composable
fun SizeDetails(size: String){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .background(
                colorResource(id = R.color.white50),
                RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(10.dp)
        ){
            val textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )

            Row(){
                Text(
                    text = "TAGLIA: ",
                    style = textStyle
                )

                Text(
                    text = size,
                    color = colorResource(id = R.color.red),
                    style = textStyle
                )
            }

            Row{
                Text(
                    text = "PREZZO: ",
                    style = textStyle
                )

                Text(
                    text = "89.99€",
                    color = colorResource(id = R.color.red),
                    style = textStyle
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(45.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red)),
            onClick = { /*TODO*/ },

            ) {
            Text(
                text = "AGGIUNGI AL CARRELLO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

