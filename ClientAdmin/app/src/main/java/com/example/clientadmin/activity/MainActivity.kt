package com.example.clientadmin.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.clientadmin.R
import com.example.clientadmin.ui.theme.ClientAdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClientAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.primary)
                ) {
                    val globalIndex = remember { mutableIntStateOf(0)}

                    when (globalIndex.intValue) {
                        0 -> IndexPage(globalIndex)
                        1 -> Login(globalIndex)
                        //2 -> Register()
                        3 -> Scaffold(globalIndex)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp(){
    val globalIndex = remember { mutableIntStateOf(0)}

    when (globalIndex.intValue) {
        0 -> IndexPage(globalIndex)
        1 -> Login(globalIndex)
        //2 -> Register()
        3 -> Scaffold(globalIndex)
    }
}
