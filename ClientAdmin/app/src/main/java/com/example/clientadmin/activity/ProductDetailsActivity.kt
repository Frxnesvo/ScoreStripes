package com.example.clientadmin.activity

import android.graphics.Bitmap
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.ProductFormViewModel
import com.example.clientadmin.viewmodels.ProductState
import com.example.clientadmin.viewmodels.ProductViewModel
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.viewmodels.ClubViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun ProductDetails(productViewModel: ProductViewModel, productFormViewModel: ProductFormViewModel, clubViewModel: ClubViewModel, navHostController: NavHostController, isAdd: Boolean = false){
    val productState by productFormViewModel.productState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp)
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

            Text(text = stringResource(id = R.string.details), style = style)

            ImagesProduct(productState)

            TextFieldString(
                value = remember { mutableStateOf(productState.name) },
                text = stringResource(id = R.string.name),
                onValueChange = { productFormViewModel.updateName(it)}
            )

            ComboBox(
                options = clubViewModel.clubNames,
                selectedOption = remember { mutableStateOf(productState.club) },
                onValueChange = { productFormViewModel.updateClub(it) }
            )

            ComboBox(
                options = flowOf(Gender.entries),
                selectedOption = remember { mutableStateOf(productState.gender.toString()) },
                onValueChange = { productFormViewModel.updateGender(Gender.valueOf(it))}
            )

            ComboBox(
                options = flowOf(ProductCategory.entries),
                selectedOption = remember { mutableStateOf(productState.productCategory.toString()) },
                onValueChange = { productFormViewModel.updateCategory(ProductCategory.valueOf(it))}
            )

            TextFieldString(
                value = remember { mutableStateOf(productState.brand) },
                text = stringResource(id = R.string.brand),
                onValueChange = { productFormViewModel.updateBrand(it)}
            )

            TextFieldDouble(
                value = remember { mutableStateOf(productState.price) },
                text = stringResource(id = R.string.price),
                onValueChange = { productFormViewModel.updatePrice(it.toDouble())}
            )

            TextFieldString(
                value = remember { mutableStateOf(productState.description) },
                text = stringResource(id = R.string.description),
                lines = 10,
                onValueChange = { productFormViewModel.updateDescription(it)}
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.quantities), style = style)

            productState.variants.forEach{
                variant -> TextFieldInt(value = remember { mutableStateOf(variant.value) }, text = variant.key.name) {
                    productFormViewModel.updateVariant(variant.key, it.toInt())
                }
            }

        }

        ButtonCustom(
            text = if(isAdd) stringResource(id = R.string.create) else stringResource(id = R.string.update),
            background = R.color.secondary
        ) {
            if (isAdd) {
                productViewModel.addProduct(
                    productState.name,
                    productState.club,
                    productState.gender,
                    productState.productCategory,
                    productState.description,
                    productState.pic1!!,
                    productState.pic2!!,
                    productState.price,
                    productState.variants
                )
            } else {
                productViewModel.updateProduct(
                    productState.name,
                    productState.club,
                    productState.gender,
                    productState.productCategory,
                    productState.description,
                    productState.pic1!!,
                    productState.pic2!!,
                    productState.price,
                    productState.variants
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
        BoxImage(pic = productState.pic1, fraction = .5f)
        BoxImage(pic = productState.pic2)
    }
}

@Composable
fun BoxImage(pic: Bitmap?, fraction: Float = 1f){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(fraction)
            .fillMaxHeight()
    ){
        pic?.let {
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
