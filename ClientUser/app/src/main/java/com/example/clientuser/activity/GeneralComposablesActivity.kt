package com.example.clientuser.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientuser.R

import java.time.LocalDate


@Composable
fun Title(colorText: Color = colorResource(id = R.color.black)){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = stringResource(id = R.string.score),
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.secondary),
            fontWeight = FontWeight.SemiBold

        )
        Text(
            text = stringResource(id = R.string.stripes),
            style = MaterialTheme.typography.titleLarge,
            color = colorText,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun IconButtonBar(
    imageVector: ImageVector?,
    navHostController: NavHostController,
    onClick: () -> Unit
){
    Row(
        horizontalArrangement = if (imageVector != null) Arrangement.SpaceBetween else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
    ){
        BoxIcon(
            iconColor = colorResource(id = R.color.secondary),
            content = Icons.AutoMirrored.Rounded.KeyboardArrowLeft
        ) { navHostController.popBackStack() }

        if (imageVector != null) {
            BoxIcon(
                background = colorResource(id = R.color.secondary),
                iconColor = colorResource(id = R.color.white),
                content = imageVector
            ) { onClick() }
        }
    }
}

@Composable
fun BoxIcon(
    background: Color = colorResource(id = R.color.white),
    size: Dp = 40.dp,
    iconColor : Color,
    content: Any,
    onclick: () -> Unit)
{
    Box(
        modifier = Modifier
            .clickable { onclick() }
            .size(size)
            .background(background, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ){
        when (content) {
            is ImageVector -> {
                Icon(
                    imageVector = content,
                    contentDescription = null,
                    tint = iconColor
                )
            }
            is String -> {
                Text(
                    text = content,
                    color = iconColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            is Bitmap -> {
                Image(
                    bitmap = content.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, iconColor, CircleShape)
                )
            }
        }
    }
}

@Composable
fun BoxImage(
    height: Dp = 150.dp,
    boxTitle: String,
    painter: Painter,
    onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(30.dp)
    )
    {
        Box{
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black50))
                .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
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
fun CustomComboBox(
    options: List<Any>,
    text: String,
    selectedOption: String,
    readOnly: Boolean = true,
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
                enabled = !readOnly,
                value = selectedOption,
                onValueChange = {},
                label = {
                    Text(
                        text = text,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
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
                    focusedLabelColor = colorResource(id = R.color.secondary),
                    unfocusedLabelColor = colorResource(id = R.color.black),
                    cursorColor = Color.Transparent
                )
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
                        text = { Text(text = stringResource(id = R.string.list_empty)) },
                        onClick = {expanded = false}
                    )
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    textColor: Color = colorResource(id = R.color.white),
    background: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
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
                letterSpacing = 5.sp,
                color = textColor               //TODO vedere se va bene
            )
        )
    }
}

@Composable
fun ImagePicker(
    image: Bitmap?,
    isError: Boolean = false,
    errorMessage: String = "",
    size: Dp,
    onChange: (Bitmap?) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            onChange(BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)))
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .background(colorResource(id = R.color.white), RoundedCornerShape(30.dp))
                .border(
                    width = 1.5.dp,
                    color = colorResource(id = if (isError) R.color.secondary else R.color.transparent),
                    shape = RoundedCornerShape(30.dp)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { launcher.launch("image/*") }
        ) {
            if (image != null)
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(30.dp))
                )
            else
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = null,
                    tint = colorResource(id = R.color.red)
                )
        }
        if(isError)
            Text(
                text = errorMessage,
                color = colorResource(id = R.color.red),
                modifier = Modifier.padding(5.dp)
            )
    }
}

@Composable
fun CustomTextField(
    value: String,
    text: String,
    isError: Boolean = false,
    errorMessage: String = "",
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
        errorBorderColor = colorResource(id = R.color.red),
    )

    Column {
        OutlinedTextField(
            enabled = !readOnly,
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
            isError = isError,
            leadingIcon = leadingIcon?.let { { Icon(imageVector = it, contentDescription = null) } },
            maxLines = lines,
            singleLine = lines <= 1,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = colors,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )

        if(isError)
            Text(
                text = errorMessage,
                color = colorResource(id = R.color.red),
                modifier = Modifier.padding(5.dp)
            )
    }
}

@Composable
fun CustomDatePicker( //TODO da migliorare esteticamente
    date: LocalDate,
    isError: Boolean,
    errorMessage: String,
    text: String,
    onValueChange: (LocalDate) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Text(text = text)
        CustomComboBox(
            options = (1..31).toList(),
            text = stringResource(id = R.string.day),
            selectedOption = date.dayOfMonth.toString()
        ) { onValueChange(LocalDate.of(date.year, date.month, it.toInt())) }
        CustomComboBox(
            options = (1..12).toList(),
            text = stringResource(id = R.string.month),
            selectedOption = date.monthValue.toString()
        ) { onValueChange(LocalDate.of(date.year, it.toInt(), date.dayOfMonth)) }
        CustomComboBox(
            options = (LocalDate.now().year downTo 1900).toList(),
            text = stringResource(id = R.string.year),
            selectedOption = date.year.toString()
        ) { onValueChange(LocalDate.of(it.toInt(), date.month, date.dayOfMonth)) }
        if(isError)
            Text(
                text = errorMessage,
                color = colorResource(id = R.color.red),
                modifier = Modifier.padding(5.dp)
            )
    }
}

@Composable
fun SwipeToDismissDeleteRow(){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.secondary50)),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = colorResource(id = R.color.primary)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissItem(content: @Composable () -> Unit, onRemove : () -> Unit) {

    val isRemove = remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if(dismissValue == SwipeToDismissBoxValue.EndToStart){
                onRemove()
                isRemove.value = true
                true
            }else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            if(dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart)
                SwipeToDismissDeleteRow()
        }
    ) {
        when(dismissState.currentValue){
            SwipeToDismissBoxValue.StartToEnd -> {}
            SwipeToDismissBoxValue.EndToStart -> {}
            SwipeToDismissBoxValue.Settled -> {
                if(!isRemove.value) content()
            }
        }
    }
}