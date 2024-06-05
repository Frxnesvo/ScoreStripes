package com.example.clientuser.model

import android.net.Uri
import com.example.clientuser.model.enumerator.Gender

import java.time.LocalDate

class Customer(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val pic: Uri,
    val favoriteTeam: String
    //TODO aggiungere gli altri campi
) {
    init {
        require(validateProfilePic(pic)) { "Invalid profile picture" }
        require(validateUsername(username)) { "Invalid username: must be between 3 and 20 characters" }
        require(validateFirstName(firstName)) { "Invalid first name: must be between 3 and 20 characters" }
        require(validateLastName(lastName)) { "Invalid last name: must be between 3 and 20 characters" }
        require(validateEmail(email)) { "Invalid email format" }
        require(validateBirthdate(birthDate)) { "Invalid birthdate: must be before the current date" }
    }

    companion object {
        //TODO matchare i controlli di validazione con quelli del backend
        fun validateProfilePic(pic: Uri): Boolean {
            return pic == Uri.EMPTY
        }
        fun validateUsername(username: String): Boolean {
            return username.length in 3..20
        }
        fun validateFirstName(firstName: String): Boolean {
            return firstName.length in 3..20
        }
        fun validateLastName(lastName: String): Boolean {
            return lastName.length in 3..20
        }
        fun validateEmail(email: String): Boolean {
            return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        }
        fun validateBirthdate(birthdate: LocalDate): Boolean {
            return birthdate.isBefore(LocalDate.now())
        }

    }
}