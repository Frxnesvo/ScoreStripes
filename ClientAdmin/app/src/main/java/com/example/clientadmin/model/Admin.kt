package com.example.clientadmin.model

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
    private val picUrl: String,
) {
    val pic: Painter?

    init {
        pic = null //Prendere l'immagine dal bucket
    }

}