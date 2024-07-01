package com.example.clientuser.model

import android.graphics.Bitmap
import com.example.clientuser.model.dto.AuthResponseDto
import com.example.clientuser.utils.S3ImageDownloader
import com.example.clientuser.model.enumerator.Gender

import java.time.LocalDate

class Customer(
    val id: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val birthDate: LocalDate = LocalDate.now(),
    val gender: Gender = Gender.MALE,
    val pic: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val favoriteTeam: String = "",
    val addresses: List<Address> = emptyList()
) {

    companion object {
        suspend fun fromDto(customerDto: AuthResponseDto): Customer {
            return Customer(
                id = customerDto.id,
                username = customerDto.username,
                firstName = customerDto.firstName,
                lastName = customerDto.lastName,
                email = customerDto.email,
                birthDate = customerDto.birthDate,
                gender = customerDto.gender,
                favoriteTeam = customerDto.favouriteTeam,
                addresses = listOf(customerDto.address),
                pic = S3ImageDownloader.getImageFromBucket(customerDto.profilePicUrl)
            )
        }


        //TODO matchare i controlli di validazione con quelli del backend
        fun validateProfilePic(pic: Bitmap?): Boolean {
            return pic != null
        }

        fun validateUsername(username: String): Boolean {
            return username.matches("^(?=.*[a-z])(?=.*[A-Z]).+$".toRegex()) && username.isNotBlank()
        }

        fun validateEmail(email: String): Boolean {
            return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        }

        fun validateBirthdate(birthdate: LocalDate): Boolean {
            return birthdate.isBefore(LocalDate.now())
        }

        fun validateFavouriteTeam(favouriteTeam: String): Boolean {
            return favouriteTeam.isEmpty()
        }

        fun validateAddress(address: Address): Boolean {
            return Address.validateStreet(address.street) && Address.validateCity(address.city) && Address.validateState(address.state)
                    && Address.validateZipCode(address.zipCode) && Address.validateCivicNumber(address.civicNumber)
        }

    }
}