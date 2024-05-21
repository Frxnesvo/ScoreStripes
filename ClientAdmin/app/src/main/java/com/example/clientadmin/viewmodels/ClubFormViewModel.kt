package com.example.clientadmin.viewmodels

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val image: Uri = Uri.EMPTY,
    val league: String = "",
    //error
    val isNameError: Boolean = !Club.validateName(name),
    val isImageError: Boolean = !Club.validateImage(image),
    val isLeagueError: Boolean = !League.validateName(league)
)
class ClubFormViewModel {
    private val _clubState = MutableStateFlow(ClubState())
    val clubState: StateFlow<ClubState> = _clubState.asStateFlow()

    fun updateNameClub(nameTeam: String) {
        val hasError = !Club.validateName(nameTeam)
        _clubState.value = _clubState.value.copy(
            name = nameTeam,
            isNameError = hasError
        )
    }
    fun updateImage(image: Uri) {
        val hasError = !Club.validateImage(image)
        _clubState.value = _clubState.value.copy(
            image = image,
            isImageError = hasError
        )
    }
    fun updateLeague(nameLeague: String) {
        val hasError = !League.validateName(nameLeague)
        _clubState.value = _clubState.value.copy(
            league = nameLeague,
            isLeagueError = hasError
        )
    }
}