package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R


//TODO aggiungere lo scroll

@Preview(showBackground = true)
@Composable
fun Cart(){
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(10.dp, 20.dp, 10.dp, 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Title()
        SummaryBar()


        //TODO USARE LAZYCOLUMN
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            repeat(3){
                CartProduct()
            }
        }

    }
}


@Composable
fun SummaryBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(colorResource(id = R.color.white), RoundedCornerShape(30.dp)),   //TODO mettere colore bianco
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(10.dp),

        ){
            Row{
                val textStyle = TextStyle(
                    fontSize = 18.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = "TOTALE: ",
                    style = textStyle
                )

                Text(
                    text = "89.99",                             //TODO
                    style = textStyle,
                    color = colorResource(id = R.color.red)
                )
            }

            Text(
                text = "4 prodotti",
                color = colorResource(id = R.color.black50),
                style = TextStyle(fontSize = 14.sp)
            )
        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red)),
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(30.dp),
        ) {
            Text(
                text = "ACQUISTA",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
            )
        }
    }
}

@Composable
fun CartProduct(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ){
        ProductImage(size = 80)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ){
                ProductDetail(
                    team = "ARSENAL",
                    kit = "Homekit 23/24",
                    teamFont = 16,
                    kitFont = 12,
                    teamSpacing = 0,
                    kitSpacing = 0,
                    gap = 5,
                    fontWeight = FontWeight.Normal
                )

                SizeIcon(letter = "M",
                    size = 30,
                    background = colorResource(id = R.color.white),
                    sizeColor = colorResource(id = R.color.red),
                    font = 14,
                    onClick = {}
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "89.99€",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                ){

                    //TODO mettere icona "-" al posto della freccia
                    Icon(
                        background = colorResource(id = R.color.red),
                        icon = Icons.Outlined.ArrowBack,
                        size = 20,
                        iconColor =  colorResource(R.color.white),
                        iconSize = 16,
                        onclick = {}
                    )

                    Text(
                        text = "1",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Icon(
                        background = colorResource(id = R.color.red),
                        icon = Icons.Outlined.Add,
                        size = 20,
                        iconColor = colorResource(R.color.white),
                        iconSize = 16,
                        onclick = {})
                }
            }
        }
    }
    


}

