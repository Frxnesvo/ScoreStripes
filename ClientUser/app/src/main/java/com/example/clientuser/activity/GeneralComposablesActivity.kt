package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.Enum.Gender
import com.example.clientuser.model.Product
import com.example.clientuser.model.User
import java.time.LocalDate



@Composable
fun Title(colorText: Color = colorResource(id = R.color.black)){
    val titleStyle = TextStyle(
        fontSize = 26.sp,
        letterSpacing = 5.sp,
        fontWeight = FontWeight.SemiBold,
    )

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            color = colorResource(id = R.color.red),
            text = "SCORE",
            style = titleStyle

        )
        Text(
            text = "STRIPES",
            style = titleStyle,
            color = colorText
        )
    }
}

@Composable
fun GeneralButton(height: Int, imageId: Int, text: String, textColorId: Int, onClick: () -> Unit) : Unit{
    Box(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.black50), RoundedCornerShape(30.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "button image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
        )

        ButtonText(text = text, colorId = textColorId)
    }
}

@Composable
fun Icon(background: Color, icon: ImageVector, size: Int, iconColor : Color, iconSize: Int = 24, onclick: () -> Unit){
    Box(
        modifier = Modifier
            .clickable { onclick() }
            .size(size.dp)
            .background(background, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = icon,
            contentDescription = "icona",
            tint = iconColor,
            modifier = Modifier
                .size(iconSize.dp)
        )
    }
}

@Composable
fun SizeIcon(letter: String, size: Int, background: Color, sizeColor: Color, font: Int, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .size(size.dp)
            .background(background, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = letter,
            color = sizeColor,
            style = TextStyle(
                fontSize = font.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}



@Composable
fun ButtonText(text: String, colorId: Int){
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            fontSize = 22.sp,
            letterSpacing = 5.sp),
        color = colorResource(id = colorId),
    )
}

//TODO passargli i prodotti
@Composable
fun ProductImage(size: Int){
    Image(
        painter = painterResource(id = R.drawable.product),
        contentDescription = "product",
        modifier = Modifier
            .width(size.dp)      //TODO vanno bene fissate?
            .height(size.dp),
        contentScale = ContentScale.Crop
    )
}


//TODO da fare i recfactor con product
@Composable
fun ProductDetail(team: String, kit: String, teamFont: Int, kitFont: Int, teamSpacing: Int, kitSpacing: Int, gap: Int, fontWeight: FontWeight){
    Column(
        verticalArrangement = Arrangement.spacedBy(gap.dp),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "ARSENAL",
            style = TextStyle(
                fontSize = teamFont.sp,
                fontWeight = fontWeight,
                letterSpacing = teamSpacing.sp
            )
        )

        Text(
            text = "Homekit 23/24",
            style = TextStyle(
                fontSize = kitFont.sp,
                letterSpacing = kitSpacing.sp
            ),
            color = colorResource(id = R.color.black50)
        )
    }
}


//TODO passare l'immagine dell'utente che bisogna visualizzare
@Composable
fun ImageProfile(size: Int, onClick: () -> Unit = {}, borderColor: Color = Color.Transparent){
    Image(
        painter = painterResource(id = R.drawable.profilo), //TODO mettere immagini utente
        contentDescription = "user image",  //scritta nel caso in cui non trova l'immagine
        modifier = Modifier
            .clickable { onClick() }
            .size(size.dp)
            .clip(RoundedCornerShape(75.dp))
            .border(2.dp, borderColor, RoundedCornerShape(75)),
        contentScale = ContentScale.Crop,
    )
}