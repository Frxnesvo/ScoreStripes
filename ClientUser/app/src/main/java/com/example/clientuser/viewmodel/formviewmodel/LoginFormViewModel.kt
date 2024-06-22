package com.example.clientuser.viewmodel.formviewmodel

import android.graphics.Bitmap
import com.example.clientuser.model.Address
import com.example.clientuser.model.Customer
import com.example.clientuser.model.enumerator.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class CustomerState(
    val firstName : String = "",
    val lastName : String = "",
    val email : String = "",
    val username : String = "",
    val birthdate : LocalDate = LocalDate.now(),
    val gender : Gender = Gender.entries[0],
    val profilePic : Bitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888),
    val favouriteTeam : String = "",

    //error
    val isProfilePicError: Boolean = !Customer.validateProfilePic(profilePic),
    val isUsernameError: Boolean = !Customer.validateUsername(username),
    val isEmailError: Boolean = !Customer.validateEmail(email),
    val isBirthdateError: Boolean = !Customer.validateBirthdate(birthdate),
    val isFavouriteTeamError: Boolean = !Customer.validateFavouriteTeam(favouriteTeam),
)
class LoginFormViewModel {
    private val _customerState = MutableStateFlow(CustomerState())
    val customerState: StateFlow<CustomerState> = _customerState.asStateFlow()

    fun updateProfilePic(uri: Bitmap){
        _customerState.value = _customerState.value.copy(
            profilePic = uri,
            isProfilePicError = !Customer.validateProfilePic(uri)
        )
    }

    fun updateUsername(username: String){
        _customerState.value = _customerState.value.copy(
            username = username,
            isUsernameError = !Customer.validateUsername(username)
        )
    }

    fun updateBirthdate(birthdate: LocalDate){
        _customerState.value = _customerState.value.copy(
            birthdate = birthdate,
            isBirthdateError = !Customer.validateBirthdate(birthdate)
        )
    }

    fun updateGender(gender: Gender){
        _customerState.value = _customerState.value.copy(
            gender = gender
        )
    }

    fun updateFavouriteTeam(favouriteTeam: String){
        _customerState.value = _customerState.value.copy(
            favouriteTeam = favouriteTeam,
            isFavouriteTeamError = !Customer.validateFavouriteTeam(favouriteTeam)
        )
    }
}