package com.example.clientuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.clientuser.activity.Cart
import com.example.clientuser.activity.Home
import com.example.clientuser.activity.Icon
import com.example.clientuser.activity.ImageProfile
import com.example.clientuser.activity.UserProfile
import com.example.clientuser.activity.WishList
import com.example.clientuser.ui.theme.ClientUserTheme
import com.example.clientuser.viewmodel.ProductViewModel

enum class Screen{
    HOME,
    WISHLIST,
    CART,
    USER_PROFILE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientUserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun NavigationView(navHostController: NavHostController){

}

@Preview(showBackground = true)
@Composable
fun HomePage(){
    val selectedScreen = remember { mutableStateOf(Screen.HOME) }
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(selectedScreen) }
    ){
        it.calculateBottomPadding()
        when(selectedScreen.value){
            Screen.HOME -> Home(ProductViewModel())
            Screen.WISHLIST -> WishList()
            Screen.CART -> Cart()
            Screen.USER_PROFILE -> UserProfile()
        }
    }
}

@Composable
fun BottomBar(screen: MutableState<Screen>){
    val red: Color = colorResource(id = R.color.red)
    val white: Color = colorResource(id = R.color.white)
    val black: Color = colorResource(id = R.color.black)
    val transparent: Color = Color.Transparent

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(
                colorResource(id = R.color.white),
                RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
            )

    ){
        //Home button
        Box(
            modifier = Modifier
                .clickable { screen.value = Screen.HOME }
                .padding(10.dp)
                .background(
                    if (screen.value == Screen.HOME) red else transparent,
                    RoundedCornerShape(20.dp)
                )
                .size(40.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "SS",
                color = if (screen.value == Screen.HOME) white else black,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        //wishList button
        Icon(
            background = if (screen.value == Screen.WISHLIST) red else transparent,
            icon = Icons.Outlined.FavoriteBorder,
            size = 40,
            iconColor = if (screen.value == Screen.WISHLIST) white else black,
            onclick = {screen.value = Screen.WISHLIST}
        )

        //cart button
        Icon(
            background = if (screen.value == Screen.CART) red else transparent,
            icon = Icons.Outlined.ShoppingCart,
            size = 40,
            iconColor = if (screen.value == Screen.CART) white else black,
            onclick = {screen.value = Screen.CART}
        )

        //user profile button
        ImageProfile(
            size = 40,
            onClick = {screen.value = Screen.USER_PROFILE},
            borderColor = if(screen.value == Screen.USER_PROFILE) red else transparent
        )
    }
}