package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientuser.R
import com.example.clientuser.model.dto.AddressDto


@Composable
fun AddressItem(addressDto: AddressDto){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white50),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light, color = colorResource(id = R.color.black50))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if(addressDto.defaultAddress) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            Text(
                text = "${addressDto.street}, ${addressDto.civicNumber}",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light, color = colorResource(id = R.color.black))
            )
            if(addressDto.defaultAddress)
                Text(
                    text = stringResource(id = R.string.default_address),
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Light, color = colorResource(id = R.color.secondary))
                )
        }
        Text(
            text = addressDto.state,
            style = style
        )
        Text(
            text = addressDto.city,
            style = style
        )
        Text(
            text = addressDto.zipCode,
            style = style
        )
    }
}