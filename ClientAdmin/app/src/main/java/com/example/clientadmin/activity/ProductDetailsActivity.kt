package com.example.clientadmin.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.clientadmin.LocalClubViewModel
import com.example.clientadmin.LocalProductViewModel
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.formViewModel.ProductFormViewModel
import com.example.clientadmin.viewmodels.formViewModel.ProductState
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.model.enumerator.ProductCategory
import com.example.clientadmin.model.enumerator.Size

@Composable
fun ProductDetails(
    navHostController: NavHostController,
    productFormViewModel: ProductFormViewModel,
    id: String? = null
){
    val productViewModel = LocalProductViewModel.current

    val productState by productFormViewModel.productState.collectAsState()
    val clubs by LocalClubViewModel.current.clubs.collectAsState()

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
                isError = productState.isNameError,
                errorMessage = stringResource(id = R.string.name_error),
                text = stringResource(id = R.string.name)
            ){ productFormViewModel.updateName(it) }

            CustomComboBox(
                options = clubs.map { it.name },
                text = stringResource(id = R.string.club),
                selectedOption = if(productState.club.isEmpty()) productState.club else clubs[0].name,
            ){ productFormViewModel.updateClub(it) }

            CustomComboBox(
                options = Gender.entries,
                text = stringResource(id = R.string.gender),
                selectedOption = "${productState.gender}"
            ){ productFormViewModel.updateGender(Gender.valueOf(it)) }

            CustomComboBox(
                options = ProductCategory.entries,
                text = stringResource(id = R.string.category),
                selectedOption = "${productState.productCategory}"
            ){ productFormViewModel.updateCategory(ProductCategory.valueOf(it)) }

            CustomTextField(
                value = productState.brand,
                isError = productState.isBrandError,
                errorMessage = stringResource(id = R.string.brand_error),
                text = stringResource(id = R.string.brand)
            ){ productFormViewModel.updateBrand(it) }

            CustomTextField(
                value = if (productState.price != null) productState.price.toString() else "",
                isError = productState.isPriceError,
                text = stringResource(id = R.string.price),
                errorMessage = stringResource(id = R.string.price_error),
                keyboardType = KeyboardType.Number
            ) {
                if (it.isEmpty())
                    productFormViewModel.updatePrice(null)
                else if (it.toDoubleOrNull() != null)
                    productFormViewModel.updatePrice(it.toDouble())
            }

            CustomTextField(
                value = productState.description,
                isError = productState.isDescriptionError,
                errorMessage = stringResource(id = R.string.description_error),
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
                size ->
                CustomTextField(
                    value = productState.variants[size].toString(),
                    text = size.name,
                    keyboardType = KeyboardType.Number
                ) {
                    if (it.isEmpty())
                        productFormViewModel.updateVariant(size, 0)
                    else if (it.toIntOrNull() != null)
                        productFormViewModel.updateVariant(size, it.toInt())
                }
            }
        }

        CustomButton(
            text = if(id == null) stringResource(id = R.string.create) else stringResource(id = R.string.update),
            background = if (
                productState.isPicPrincipalError || productState.isPicSecondaryError || productState.isNameError || productState.isBrandError || productState.isDescriptionError || productState.isPriceError
            ) R.color.black50 else R.color.secondary
        ) {
            if (!productState.isPicPrincipalError && !productState.isPicSecondaryError && !productState.isNameError && !productState.isBrandError && !productState.isDescriptionError && !productState.isPriceError)
                if (id == null) {
                    if (productViewModel.addProduct(
                            name = productState.name,
                            description = productState.description,
                            price = productState.price ?: 0.0,
                            brand = productState.brand,
                            gender = productState.gender,
                            category = productState.productCategory,
                            pic1 = productState.pic1!!,
                            pic2 = productState.pic2!!,
                            club = productState.club,
                            variants = productState.variants
                        )
                    ) navHostController.navigate("products")
                } else {
                    productViewModel.updateProduct(
                        id = id,
                        description = productState.description,
                        price = productState.price ?: 0.0,
                        pic1 = productState.pic1!!,
                        pic2 = productState.pic2!!,
                        variants = productState.variants
                    )
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
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        ImagePicker(
            pic = productState.pic1,
            isError = productState.isPicPrincipalError,
            errorMessage = stringResource(id = R.string.pic_error),
            size = 80.dp
        ){ productFormViewModel.updatePic1(it) }

        ImagePicker(
            pic = productState.pic2,
            isError = productState.isPicSecondaryError,
            errorMessage = stringResource(id = R.string.pic_error),
            size = 80.dp
        ){ productFormViewModel.updatePic2(it) }
    }
}
