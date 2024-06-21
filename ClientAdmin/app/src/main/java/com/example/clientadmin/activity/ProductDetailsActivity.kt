package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.ProductCreateRequestDto
import com.example.clientadmin.model.dto.ProductUpdateRequestDto
import com.example.clientadmin.viewmodels.formViewModel.ProductFormViewModel
import com.example.clientadmin.viewmodels.formViewModel.ProductState
import com.example.clientadmin.viewmodels.ProductViewModel
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size
import com.example.clientadmin.viewmodels.ClubViewModel

@Composable
fun ProductDetails(
    productViewModel: ProductViewModel,
    productFormViewModel: ProductFormViewModel,
    clubViewModel: ClubViewModel,
    navHostController: NavHostController,
    id: String? = null
){
    val productState by productFormViewModel.productState.collectAsState()
    val clubs by clubViewModel.clubNames.collectAsState()
    val error by productViewModel.addError

    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
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

            ImagesProduct(
                productFormViewModel = productFormViewModel,
                productState = productState
            )

            CustomTextField(
                value = productState.name,
                text = stringResource(id = R.string.name)
            ){ productFormViewModel.updateName(it) }

            CustomComboBox(
                options = clubs,
                expandable = clubs.isNotEmpty(),
                selectedOption = if (clubs.isNotEmpty()) clubs[0] else ""
            ){ productFormViewModel.updateClub(it) }

            CustomComboBox(
                options = Gender.entries,
                selectedOption = "${productState.gender}"
            ){ productFormViewModel.updateGender(Gender.valueOf(it)) }

            CustomComboBox(
                options = ProductCategory.entries,
                selectedOption = "${productState.productCategory}"
            ){ productFormViewModel.updateCategory(ProductCategory.valueOf(it)) }

            CustomTextField(
                value = productState.brand,
                text = stringResource(id = R.string.brand)
            ){ productFormViewModel.updateBrand(it) }

            CustomTextField(
                value = productState.price.toString(),
                text = stringResource(id = R.string.price),
                keyboardType = KeyboardType.Decimal
            ){ productFormViewModel.updatePrice(it.toDouble()) }

            CustomTextField(
                value = productState.description,
                text = stringResource(id = R.string.description),
                lines = 10
            ){ productFormViewModel.updateDescription(it) }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.quantities), style = style)

            Size.entries.forEach{
                variant ->
                CustomTextField(
                    value = productState.variants[variant].toString(),
                    text = variant.name,
                    keyboardType = KeyboardType.Number
                ) {
                    val quantity = if(it != "") it.toInt() else 0
                    productFormViewModel.updateVariant(variant, quantity)
                }
            }
        }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = colorResource(id = R.color.red)
            )
        }

        CustomButton(
            text = if(id == null) stringResource(id = R.string.create) else stringResource(id = R.string.update),
            background = R.color.secondary
        ) {
            //TODO vanno fatti tutti i vari controlli?
            if (id == null) {
                val club = if(productState.club != "") productState.club else clubs[0]
                val productRequestDto = ProductCreateRequestDto(
                    name = productState.name,
                    club = club,
                    brand = productState.brand,
                    gender = productState.gender,
                    productCategory = productState.productCategory,
                    description =  productState.description,
                    price = productState.price,
                    variants = productState.variants
                )
                if (productViewModel.addProduct(productRequestDto, productState.pic1, productState.pic2)) {
                    navHostController.popBackStack()
                }
            } else {
                val productUpdateRequestDto = ProductUpdateRequestDto(
                    description = productState.description,
                    price = productState.price,
                    variants = productState.variants,
                )
                productViewModel.updateProduct(id, productUpdateRequestDto, productState.pic1, productState.pic2)
            }
        }
    }
}

@Composable
fun ImagesProduct(productFormViewModel: ProductFormViewModel, productState: ProductState){
    Row(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(30.dp)
            )
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        ImagePicker(
            pic = productState.pic1,
            size = 80.dp
        ){
            uri ->
            if(uri != null) productFormViewModel.updatePic1(uri)
        }

        ImagePicker(
            pic = productState.pic2,
            size = 80.dp
        ){
            uri ->
            if(uri != null) productFormViewModel.updatePic2(uri)
        }
    }
}
