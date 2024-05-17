package com.example.clientadmin.activity

import ClubViewModel
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.ClubFormViewModel
import com.example.clientadmin.viewmodels.LeagueFormViewModel
import com.example.clientadmin.viewmodels.LeagueViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPanel(
    onDismissRequest: () -> Unit,
    navHostController: NavHostController,
    setBottomSheet: (Boolean) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonCustom(text = "CREATE PRODUCT", background = R.color.secondary) {
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        navHostController.navigate("addProduct")
                    }
                }
            }

            ButtonCustom(text = "CREATE LEAGUE", background = R.color.transparent) {
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        navHostController.navigate("addLeague")
                    }
                }
            }

            ButtonCustom(text = "CREATE TEAM", background = R.color.transparent) {
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        navHostController.navigate("addTeam")
                    }
                }
            }
        }
    }
}

@Composable
fun LeagueDetails(leagueViewModel: LeagueViewModel, leagueFormViewModel: LeagueFormViewModel, navHostController: NavHostController, isAdd: Boolean = false) {
    val leagueState by leagueFormViewModel.leagueState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        BoxImage(image = leagueState.imageLeague)

        TextFieldString(
            value = remember { mutableStateOf(leagueState.name) },
            text = "NAME LEAGUE",
            keyboardType = KeyboardType.Text,
            onValueChange = {  }
        )

        ButtonCustom(text = "ADD LEAGUE", background = R.color.secondary) {
            leagueViewModel.addLeague(
                leagueState.name,
                leagueState.imageLeague
            )
        }
    }
}

@Composable
fun ClubDetails(leagueViewModel: LeagueViewModel, clubViewModel: ClubViewModel, clubFormViewModel: ClubFormViewModel, navHostController: NavHostController, isAdd: Boolean = false) {
    val clubState by clubFormViewModel.clubState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        BoxImage(image = clubState.imageClub)

        TextFieldString(
            value = remember { mutableStateOf(clubState.name) },
            text = "NAME CLUB",
            keyboardType = KeyboardType.Text,
        ){
            name -> clubFormViewModel.updateNameClub(name)
        }


        ComboBox(
            options = leagueViewModel.leaguesNames ,
            selectedOption = remember {
                if (leagueViewModel.leaguesNames.value.isNotEmpty())
                    mutableStateOf(leagueViewModel.leaguesNames.value[0])
                else
                    mutableStateOf("")
            })
        {
            league -> clubFormViewModel.updateLeague(league)
        }

        ButtonCustom(text = "ADD LEAGUE", background = R.color.secondary) {
            clubViewModel.addClub(
                clubState.name,
                clubState.imageClub,
                clubState.league
            )
        }
    }
}
@Composable
fun BoxImage(image: Bitmap?){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .background(colorResource(id = R.color.white), RoundedCornerShape(30.dp))
    ){
        image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
            )
        } ?:
        Icon(
            imageVector = Icons.Outlined.AddCircle,
            contentDescription = "addImage",
            tint = colorResource(id = R.color.secondary)
        )
    }
}

