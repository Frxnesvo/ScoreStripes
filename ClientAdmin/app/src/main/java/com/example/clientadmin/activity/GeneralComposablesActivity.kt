package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientadmin.R
import com.example.clientadmin.model.User


@Composable
fun Title(colorStripes: Color = colorResource(id = R.color.black)){
    val style = TextStyle(fontSize = 24.sp, letterSpacing = 5.sp)

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text(text = "SCORE", color = colorResource(id = R.color.secondary), fontWeight = FontWeight.SemiBold, style = style)
        Text(text = "STRIPES", color = colorStripes, fontWeight = FontWeight.Light, style = style)
    }
}

@Composable
fun Search(name: String, onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = name, color = colorResource(id = R.color.black), style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(colorResource(id = R.color.white), RoundedCornerShape(20.dp))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Search, contentDescription = "search", tint = colorResource(id = R.color.secondary))
        }
    }
}


@Composable
fun Back(onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .background(colorResource(id = R.color.white), RoundedCornerShape(20.dp))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.KeyboardArrowLeft, contentDescription = "back", tint = colorResource(id = R.color.secondary))
        }
    }
}

@Composable
fun BoxImage(boxTitle: String, painter: Painter, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(30.dp)
    )
    {
        Box{
            Image(
                painter = painter,
                contentDescription = "image description",
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black50))
                .padding(10.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = boxTitle,
                    color = colorResource(id = R.color.white),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp),
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }

    }
}

@Composable
fun SubSectionUser(user: User, subSectionName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val modifier = Modifier
            .size(40.dp)
            .background(colorResource(id = R.color.secondary), RoundedCornerShape(20.dp))
        val style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp)

        Text(text = subSectionName, color = colorResource(id = R.color.black), style = style)
        if (user.image == null)
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.first().uppercase(),
                    color = colorResource(id = R.color.white),
                    style = style
                )
            }
        else
            Image(
                painter = user.image,
                contentDescription = "userImg",
                modifier = modifier
            )
    }
}

@Composable
fun TextFieldString(value: MutableState<String>, text: String, readOnly: Boolean = false, lines: Int = 1, keyboardType: KeyboardType = KeyboardType.Text, onValueChange: (String) -> Unit){
    val colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = colorResource(id = R.color.white),
        focusedTextColor = colorResource(id = R.color.black),
        unfocusedTextColor = colorResource(id = R.color.black50),
        focusedBorderColor = colorResource(id = R.color.secondary),
        unfocusedBorderColor = colorResource(id = R.color.white),
        focusedLabelColor = colorResource(id = R.color.secondary),
        unfocusedLabelColor = colorResource(id = R.color.black),
        errorContainerColor = colorResource(id = R.color.secondary50)
    )

    OutlinedTextField(
        readOnly = readOnly,
        value = value.value,
        onValueChange = {value.value = it },
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        maxLines = lines,
        singleLine = lines <= 1,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = colors,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        )
    )
}

@Composable
fun TextFieldDouble(value: MutableState<Double>, text: String, readOnly: Boolean = false, onValueChange: (String) -> Unit){
    val colors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = colorResource(id = R.color.white),
        focusedTextColor = colorResource(id = R.color.black),
        unfocusedTextColor = colorResource(id = R.color.black50),
        focusedBorderColor = colorResource(id = R.color.secondary),
        unfocusedBorderColor = colorResource(id = R.color.white),
        focusedLabelColor = colorResource(id = R.color.secondary),
        unfocusedLabelColor = colorResource(id = R.color.black),
        errorContainerColor = colorResource(id = R.color.secondary50)
    )

    OutlinedTextField(
        readOnly = readOnly,
        value = value.value.toString(),
        onValueChange = onValueChange,
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = colors,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(options: List<Any>, selectedOption: MutableState<String>, fraction: Float = 1f, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(fraction)
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded},
        ) {
            OutlinedTextField(
                value = selectedOption.value,
                onValueChange = onValueChange,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                readOnly = false,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.white),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    focusedBorderColor = colorResource(id = R.color.secondary),
                    unfocusedBorderColor = colorResource(id = R.color.white),
                    errorContainerColor = colorResource(id = R.color.secondary50)
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.toString()) },
                        onClick = {
                            selectedOption.value = "$option"
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonCustom(text: String, background: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = colorResource(id = background),
                contentColor = colorResource(
                    if (background != R.color.transparent) R.color.white
                    else R.color.secondary
                )
            ),
        shape = RoundedCornerShape(size = 30.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 5.sp
            )
        )
    }
}