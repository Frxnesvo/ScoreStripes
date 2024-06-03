package com.example.clientadmin.activity

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.clientadmin.R
import java.time.LocalDate


@Composable
fun Title(colorStripes: Color = colorResource(id = R.color.black)){
    val style = TextStyle(fontSize = 24.sp, letterSpacing = 5.sp)

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = R.string.score),
            color = colorResource(id = R.color.secondary),
            fontWeight = FontWeight.SemiBold,
            style = style
        )
        Text(
            text = stringResource(id = R.string.stripes),
            color = colorStripes,
            fontWeight = FontWeight.Light,
            style = style
        )
    }
}

@Composable
fun Search(name: String, onClick: () -> Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            color = colorResource(id = R.color.black),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp)
        )
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
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, contentDescription = "back", tint = colorResource(id = R.color.secondary))
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
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(2.dp)
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

/*@Composable
fun SubSectionUser(username: String, subSectionName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 5.sp)

        Text(text = subSectionName, color = colorResource(id = R.color.black), style = style)
        Text(text = username, color = colorResource(id = R.color.secondary), style = style)
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomComboBox(
    options: List<Any>,
    selectedOption: String,
    readOnly: Boolean = false,
    expandable: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = if (expandable) !expanded else false
            }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {  },
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

                if (options.isNotEmpty())
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = "$option") },
                            onClick = {
                                onValueChange("$option")
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
            options = (1..31).toList(),
            selectedOption = date.value.dayOfMonth.toString()
        ) { onValueChange(LocalDate.of(date.value.year, date.value.month, it.toInt())) }
        CustomComboBox(
            options = (1..12).toList(),
            selectedOption = date.value.monthValue.toString()
        ) { onValueChange(LocalDate.of(date.value.year, it.toInt(), date.value.dayOfMonth)) }
        CustomComboBox(
            options = (LocalDate.now().year downTo 1900).toList(),
            selectedOption = date.value.year.toString()
        ) { onValueChange(LocalDate.of(it.toInt(), date.value.month, date.value.dayOfMonth)) }
    }
}