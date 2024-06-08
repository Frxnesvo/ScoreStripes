package com.example.clientadmin.service

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter


//todo vedere se ancora necessaria
class LocalDateAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    @ToJson
    fun toJson(date: LocalDate): String {
        return date.format(formatter)
    }

    @FromJson
    fun fromJson(dateString: String): LocalDate {
        return LocalDate.parse(dateString, formatter)
    }
}
