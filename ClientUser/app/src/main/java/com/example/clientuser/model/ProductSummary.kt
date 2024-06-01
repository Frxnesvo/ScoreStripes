package com.example.clientuser.model

import android.net.Uri
import com.example.clientuser.model.enumerator.Size

class ProductSummary(
    val id: String,
    val name: String,
    val clubName: String,
    val leagueName: String,
    val picUrl: String,
    val variants: Map<Size, Int>
) {
    private var pic = Uri.EMPTY
    init{
        TODO( "get a pic from bucket")
    }

    fun getPic(): Uri{
        return pic
    }
}