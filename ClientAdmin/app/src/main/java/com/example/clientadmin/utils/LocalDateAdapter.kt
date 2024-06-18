package com.example.clientadmin.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @ToJson
    fun toJson(value: LocalDate?): String? {
        return value?.format(formatter)
    }

    @FromJson
    fun fromJson(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }
}