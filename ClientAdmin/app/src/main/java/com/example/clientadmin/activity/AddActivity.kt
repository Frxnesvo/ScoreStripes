package com.example.clientadmin.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.viewmodels.formViewModel.ClubFormViewModel
import com.example.clientadmin.viewmodels.ClubViewModel
import com.example.clientadmin.viewmodels.formViewModel.LeagueFormViewModel
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
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomButton(text = "CREATE PRODUCT", background = R.color.secondary) {
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        navHostController.navigate("addProduct")
                    }
                }
            }

            CustomButton(text = "CREATE LEAGUE", background = R.color.transparent) {
                if(sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                        setBottomSheet(false)
                        navHostController.navigate("addLeague")
                    }
                }
            }

            CustomButton(text = "CREATE TEAM", background = R.color.transparent) {
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
fun LeagueDetails(leagueViewModel: LeagueViewModel, leagueFormViewModel: LeagueFormViewModel, navHostController: NavHostController) {
    val leagueState by leagueFormViewModel.leagueState.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        ImagePicker(
            imageUri = leagueState.image,
            size = 150.dp
        ){ if(it != null) leagueFormViewModel.updateImage(it) }

        CustomTextField(
            value = leagueState.name,
            text = stringResource(id = R.string.league),
            keyboardType = KeyboardType.Text
        ){ leagueFormViewModel.updateName(it) }

        CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
            leagueViewModel.addLeague(
                context,
                leagueState.name,
                leagueState.image
            )
        }
    }
}

@Composable
fun ClubDetails(leagueViewModel: LeagueViewModel, clubViewModel: ClubViewModel, clubFormViewModel: ClubFormViewModel, navHostController: NavHostController) {
    val clubState by clubFormViewModel.clubState.collectAsState()
    val leagues by leagueViewModel.leaguesNames.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        ImagePicker(
            imageUri = clubState.image,
            size = 150.dp
        ){ if(it != null) clubFormViewModel.updateImage(it) }

        CustomTextField(
            value = clubState.name,
            text = "NAME CLUB",
            keyboardType = KeyboardType.Text,
        ){ clubFormViewModel.updateNameClub(it) }


        CustomComboBox(
            options = leagues ,
            selectedOption = if (leagues.isNotEmpty()) leagues[0] else ""
        ) { clubFormViewModel.updateLeague(it) }

        CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
            clubViewModel.addClub(
                context,
                clubState.name,
                clubState.image,
                clubState.league
            )
        }
    }
}

