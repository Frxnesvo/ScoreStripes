package com.example.clientuser.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.clientuser.R
import com.example.clientuser.model.dto.AddressDto


@Composable
fun AddressItem(addressDto: AddressDto, onClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {MutableInteractionSource()},
                indication = null
            ) { onClick() }
            .background(
                color = colorResource(id = R.color.white50),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if(addressDto.defaultAddress) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            Text(
                text = "${addressDto.street}, ${addressDto.civicNumber}",
                style = MaterialTheme.typography.labelMedium,
            )
            if(addressDto.defaultAddress)
                Text(
                    text = stringResource(id = R.string.default_address),
                    style = MaterialTheme.typography.labelMedium,
                    color = colorResource(id = R.color.secondary)
                )
        }
        Text(
            text = addressDto.state,
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.black50)
        )
        Text(
            text = addressDto.city,
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.black50)
        )
        Text(
            text = addressDto.zipCode,
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.black50)
        )
    }
}