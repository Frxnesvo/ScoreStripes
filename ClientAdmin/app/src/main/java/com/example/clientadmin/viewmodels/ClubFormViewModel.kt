package com.example.clientadmin.viewmodels

import android.graphics.Bitmap
import com.example.clientadmin.model.Club
import com.example.clientadmin.model.League
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val imageClub: Bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
    val league: String = "",
    //error
    val isNameError: Boolean = !Club.validateName(name),
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
    fun updateImage(img: Bitmap) {
        _clubState.value = _clubState.value.copy(
            imageClub = img
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