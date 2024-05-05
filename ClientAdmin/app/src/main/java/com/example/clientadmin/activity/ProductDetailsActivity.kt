package com.example.clientadmin.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.Enum.Category
import com.example.clientadmin.model.Enum.Type
import com.example.clientadmin.viewmodels.ProductFormViewModel
import com.example.clientadmin.viewmodels.ProductState
import com.example.clientadmin.viewmodels.ProductViewModel

@Composable
fun ProductDetails(productViewModel: ProductViewModel, productFormViewModel: ProductFormViewModel, navHostController: NavHostController, isAdd: Boolean = false){
    val productState by productFormViewModel.productState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 80.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        val style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 5.sp
        )

        Back { navHostController.popBackStack() }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "DETAILS", style = style)

            ImagesProduct(productState)

            TextFieldString(
                value = remember { mutableStateOf(productState.team) },
                text = "TEAM",
                onValueChange = { productFormViewModel.updateTeam(it)}
            )

            TextFieldString(
                value = remember { mutableStateOf(productState.league) },
                text = "LEAGUE",
                onValueChange = { productFormViewModel.updateLeague(it)}
            )

            ComboBox(
                options = Type.entries,
                selectedOption = remember { mutableStateOf("${productState.type}") },
                onValueChange = { productFormViewModel.updateType(Type.valueOf(it))}
            )

            ComboBox(
                options = Category.entries,
                selectedOption = remember { mutableStateOf("${productState.category}") },
                onValueChange = { productFormViewModel.updateCategory(Category.valueOf(it))}
            )

            TextFieldDouble(
                value = remember { mutableStateOf(productState.price) },
                text = "PRICE",
                onValueChange = { productFormViewModel.updatePrice(it.toDouble())}
            )

            TextFieldString(
                value = remember { mutableStateOf(productState.description) },
                text = "DESCRIPTION", lines = 5,
                onValueChange = { productFormViewModel.updateDescription(it)}
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "QUANTITIES", style = style)
        }

        ButtonCustom(text = if(isAdd) "ADD PRODUCT" else "UPDATE PRODUCT", background = R.color.secondary) {
            if(isAdd) {
                productViewModel.addProduct(
                    productState.team,
                    productState.league,
                    productState.season,
                    productState.type,
                    productState.category,
                    productState.description,
                    productState.image1,
                    productState.image2,
                    productState.price,
                    productState.preferred,
                    productState.quantities
                )
            }
            else {
                productViewModel.updateProduct(
                    productState.team,
                    productState.league,
                    productState.season,
                    productState.type,
                    productState.category,
                    productState.description,
                    productState.image1,
                    productState.image2,
                    productState.price,
                    productState.preferred,
                    productState.quantities
                )
            }
        }
    }
}

@Composable
fun ImagesProduct(productState: ProductState){
    Row(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(30.dp)
            )
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        BoxImage(productState = productState, fraction = .5f)
        BoxImage(productState = productState)
    }
}

@Composable
fun BoxImage(productState: ProductState, fraction: Float = 1f){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(fraction)
            .fillMaxHeight()
    ){
        productState.image1?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
            )
        } ?:
        Icon(
            imageVector = Icons.Outlined.AddCircle,
            contentDescription = "addImage",
            tint = colorResource(id = R.color.secondary)
        )
    }
}
