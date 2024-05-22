package com.example.clientadmin.model

import android.net.Uri

class CustomerSummary (
    val id: String,
    val username: String,
    private val picUrl: String,
    val pic: Uri = Uri.EMPTY
){
    init{
        //TODO prendere immagine dal bucket
    }

    fun toQueryString(): String {
        return "$id,$username,$pic"
    }
    companion object{
        fun fromQueryString(queryString: String): CustomerSummary {
            val split = queryString.split(",")
            return CustomerSummary(split[0], split[1], split[2])
        }
    }
}