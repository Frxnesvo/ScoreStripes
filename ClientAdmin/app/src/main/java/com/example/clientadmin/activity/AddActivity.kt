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

    ModalBottomSheet(
        onDismissRequest = onDismissRequest, 
        sheetState = sheetState,
        containerColor = colorResource(id = R.color.white),
    ) {
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
            isError = leagueState.isImageError,
            errorMessage = stringResource(id = R.string.pic_error),
            size = 150.dp
        ){ leagueFormViewModel.updateImage(it) }

        CustomTextField(
            value = leagueState.name,
            isError = leagueState.isNameError,
            errorMessage = stringResource(id = R.string.name_error),
            text = stringResource(id = R.string.league),
            keyboardType = KeyboardType.Text
        ){ leagueFormViewModel.updateName(it) }

        CustomButton(
            text = stringResource(id = R.string.create),
            background = if(leagueState.isNameError || leagueState.isImageError) R.color.black50 else  R.color.secondary
        ) {
            if (!leagueState.isNameError && !leagueState.isImageError) {
                val leagueCreateRequestDto = LeagueCreateRequestDto(leagueState.name)
                if (leagueViewModel.addLeague(leagueCreateRequestDto, leagueState.image!!)) {
                    navHostController.navigate("home")
                }
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
    val leagues by leagueViewModel.leagues.collectAsState()

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
            isError = clubState.isImageError,
            errorMessage = stringResource(id = R.string.pic_error),
            size = 150.dp
        ){ if(it != null) clubFormViewModel.updateImage(it) }

        CustomTextField(
            value = clubState.name,
            isError = clubState.isNameError,
            errorMessage = stringResource(id = R.string.name_error),
            text = stringResource(id = R.string.club),
            keyboardType = KeyboardType.Text,
        ){ clubFormViewModel.updateNameClub(it) }

        CustomComboBox(
            options = leagues.map { it.name },
            text = stringResource(id = R.string.league),
            selectedOption = if (leagues.isNotEmpty()) leagues[0].name else ""
        ) { clubFormViewModel.updateLeague(it) }

        CustomButton( //todo fare l'update
            text = stringResource(id = R.string.create),
            background = if(clubState.isImageError || clubState.isNameError) R.color.black50 else R.color.secondary
        ) {
            if (!clubState.isImageError && !clubState.isNameError) {
                val clubCreateRequestDto = ClubCreateRequestDto(clubState.name, clubState.league)
                if (clubViewModel.addClub(clubCreateRequestDto, clubState.image!!)) {
                    navHostController.navigate("home")
                }
            }
        }
    }
}

