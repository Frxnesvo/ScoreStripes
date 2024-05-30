package com.example.clientuser.activity

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clientuser.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
            color = colorResource(id = R.color.secondary),
            text = stringResource(id = R.string.score),
            style = titleStyle

        )
        Text(
            text = stringResource(id = R.string.stripes),
            style = titleStyle,
            color = colorText
        )
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
            contentDescription = null,
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
                contentDescription = null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomComboBox(options: Flow<List<Any>>, selectedOption: MutableState<String>, fraction: Float = 1f, readOnly: Boolean = false, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(fraction)
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = if (readOnly) false else !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedOption.value,
                onValueChange = onValueChange,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                readOnly = readOnly,
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

                val optionList by options.collectAsState(initial = listOf())

                if (optionList.isNotEmpty())
                    optionList.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = "$option") },
                            onClick = {
                                selectedOption.value = "$option"
                                expanded = false
                            }
                        )
                    }
                else
                    DropdownMenuItem(
                        text = { Text(text = "No options available") },
                        onClick = {expanded = false}
                    )
            }
        }
    }
}

@Composable
fun CustomButton(text: String, background: Int, onClick: () -> Unit) {
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

@Composable
fun ImagePicker(
    imageUri: Uri,
    size: Dp,
    onLaunch: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { onLaunch(it) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(colorResource(id = R.color.white), RoundedCornerShape(30.dp))
            .clickable { launcher.launch("image/*") }
    ) {
        if (imageUri != Uri.EMPTY)
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
            )
        else
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "addImage",
                tint = colorResource(id = R.color.secondary)
            )
    }
}

@Composable
fun CustomTextField(
    value: String,
    text: String,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    lines: Int = 1,
    leadingIcon: ImageVector? = null,
    onValueChange: (String) -> Unit
){
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
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        leadingIcon = leadingIcon?.let { { Icon(imageVector = it, contentDescription = null) } },
        maxLines = lines,
        singleLine = lines <= 1,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = colors,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}

@Composable
fun CustomDatePicker(
    date: MutableState<LocalDate> = mutableStateOf(LocalDate.now()),
    text: String,
    onValueChange: (LocalDate) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text(text = text)
        CustomComboBox(
            options = flowOf((1..31).toList()),
            selectedOption = remember { mutableStateOf(date.value.dayOfMonth.toString()) }
        ) { onValueChange(LocalDate.of(date.value.year, date.value.month, it.toInt())) }
        CustomComboBox(
            options = flowOf((1..12).toList()),
            selectedOption = remember { mutableStateOf(date.value.monthValue.toString()) }
        ) { onValueChange(LocalDate.of(date.value.year, it.toInt(), date.value.dayOfMonth)) }
        CustomComboBox(
            options = flowOf((1900..LocalDate.now().year).toList() ),
            selectedOption = remember { mutableStateOf(date.value.year.toString()) }
        ) { onValueChange(LocalDate.of(it.toInt(), date.value.month, date.value.dayOfMonth)) }
    }
}