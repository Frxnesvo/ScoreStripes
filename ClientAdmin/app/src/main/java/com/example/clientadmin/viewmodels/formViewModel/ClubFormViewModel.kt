package com.example.clientadmin.viewmodels.formViewModel

import android.graphics.Bitmap
import com.example.clientadmin.model.Club
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClubState(
    val name: String = "",
    val image: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val league: String = "",
    //error
    val isNameError: Boolean = !Club.validateName(name)
)
class ClubFormViewModel(club: Club? = null) {
    private val _clubState = MutableStateFlow(ClubState())
    val clubState: StateFlow<ClubState> = _clubState.asStateFlow()

    init {
        if (club != null) {
            updateNameClub(club.name)
            updateImage(club.pic)
            updateLeague(club.league)
        }
    }

    fun updateNameClub(nameTeam: String) {
        val hasError = !Club.validateName(nameTeam)
        _clubState.value = _clubState.value.copy(
            name = nameTeam,
            isNameError = hasError
        )
    }
    fun updateImage(image: Bitmap) {
        _clubState.value = _clubState.value.copy(
            image = image
        )
    }
    fun updateLeague(nameLeague: String) {
        _clubState.value = _clubState.value.copy(
            league = nameLeague
        )
    }
}