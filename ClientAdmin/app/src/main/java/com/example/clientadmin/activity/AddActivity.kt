package com.example.clientadmin.activity

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.clientadmin.R
import com.example.clientadmin.model.dto.ClubCreateRequestDto
import com.example.clientadmin.model.dto.LeagueCreateRequestDto
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

            CustomButton(text = "CREATE CLUB", background = R.color.transparent) {
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
fun LeagueDetails(
    leagueViewModel: LeagueViewModel,
    leagueFormViewModel: LeagueFormViewModel,
    navHostController: NavHostController
) {
    val leagueState by leagueFormViewModel.leagueState.collectAsState()
    val error by leagueViewModel.addError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        ImagePicker(
            pic = leagueState.image,
            size = 150.dp
        ){ if(it != null) leagueFormViewModel.updateImage(it) }

        CustomTextField(
            value = leagueState.name,
            isError = leagueState.isNameError,
            text = stringResource(id = R.string.league),
            keyboardType = KeyboardType.Text
        ){ leagueFormViewModel.updateName(it) }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = colorResource(id = R.color.red)
            )
        }

        CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
            val leagueCreateRequestDto = LeagueCreateRequestDto(leagueState.name)
            if (leagueViewModel.addLeague(leagueCreateRequestDto, leagueState.image)) {
                navHostController.popBackStack()
            }
        }
    }
}

@Composable
fun ClubDetails(
    leagueViewModel: LeagueViewModel,
    clubViewModel: ClubViewModel,
    clubFormViewModel: ClubFormViewModel,
    navHostController: NavHostController
) {
    val clubState by clubFormViewModel.clubState.collectAsState()
    val leagues by leagueViewModel.leaguesNames.collectAsState()
    val error by clubViewModel.addError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Back { navHostController.popBackStack() }

        ImagePicker(
            pic = clubState.image,
            size = 150.dp
        ){ if(it != null) clubFormViewModel.updateImage(it) }

        CustomTextField(
            value = clubState.name,
            isError = clubState.isNameError,
            text = stringResource(id = R.string.club),
            keyboardType = KeyboardType.Text,
        ){ clubFormViewModel.updateNameClub(it) }

        CustomComboBox(
            options = leagues,
            expandable = leagues.isNotEmpty(),
            selectedOption = if (leagues.isNotEmpty()) leagues[0] else ""
        ) { clubFormViewModel.updateLeague(it) }

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = colorResource(id = R.color.red)
            )
        }

        CustomButton(text = stringResource(id = R.string.create), background = R.color.secondary) {
            //TODO controllo leagues non sia vuoto, immagine non vuota e nome non vuoto

            println("CLUB PIC null? --> ${clubState.image == Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)}")

            val league = if(clubState.league != "") clubState.league else leagues[0]
            val clubCreateRequestDto = ClubCreateRequestDto(clubState.name, league)
            if (clubViewModel.addClub(clubCreateRequestDto, clubState.image)){
                navHostController.popBackStack()
            }
        }
    }
}

