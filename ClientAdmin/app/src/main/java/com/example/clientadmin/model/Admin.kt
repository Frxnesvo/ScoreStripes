package com.example.clientadmin.model

import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import com.example.clientadmin.model.enumerator.Gender
import java.time.LocalDate

class Admin(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val pic: Uri = Uri.EMPTY
) {
    init {
        //TODO Prendere l'immagine dal bucket

        require(validateUsername(username)) { "Invalid username: must be between 3 and 20 characters" }
        require(validateFirstName(firstName)) { "Invalid first name: must be between 3 and 20 characters" }
        require(validateLastName(lastName)) { "Invalid last name: must be between 3 and 20 characters" }
        require(validateEmail(email)) { "Invalid email format" }
        require(validateBirthdate(birthDate)) { "Invalid birthdate: must be before the current date" }
    }

    fun toQueryString(): String {
        return "$username,$firstName,$lastName,$email,$birthDate,$gender"
    }

    companion object {
        //TODO matchare i controlli di validazione con quelli del backend
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
        fun fromQueryString(query: String): Admin {
            val parts = query.split(",")
            return Admin(
                username = parts[0],
                firstName = parts[1],
                lastName = parts[2],
                email = parts[3],
                birthDate = LocalDate.parse(parts[4]),
                gender = Gender.valueOf(parts[5])
                //picUrl = parts[6]
            )
        }
    }
}