package com.example.clientadmin.model

import android.graphics.Bitmap
import com.example.clientadmin.model.dto.AuthResponseDto
import com.example.clientadmin.model.enumerator.Gender
import com.example.clientadmin.utils.S3ImageDownloader
import java.io.Serializable
import java.time.LocalDate

class Admin(
    val username: String,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val birthDate: LocalDate ,
    val gender: Gender,
    //val pic: Bitmap
): Serializable {
    companion object {
        fun validateProfilePic(pic: Bitmap?): Boolean {
            return pic != null
        }
        fun validateUsername(username: String): Boolean {
            return username.matches("^(?=.*[a-z])(?=.*[A-Z]).+$".toRegex()) && username.isNotBlank()
        }

        fun validateBirthdate(birthdate: LocalDate): Boolean {
            return birthdate.isBefore(LocalDate.now())
        }

        fun fromDto(adminDto: AuthResponseDto): Admin{
            return Admin(
                username = adminDto.username,
                firstName = adminDto.firstName,
                lastName = adminDto.lastName,
                email = adminDto.email,
                birthDate = adminDto.birthDate,
                gender = adminDto.gender,
                //pic = S3ImageDownloader.getImageForBucket(adminDto.profilePicUrl)
            )
        }
    }
}