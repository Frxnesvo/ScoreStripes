package com.example.clientuser.activity

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clientuser.model.dto.LeagueDto

@Composable
fun LeagueItem(leagueDto: LeagueDto, onClick: () -> Unit){
    //todo mappare il dto con League
    Image(
        painter = rememberAsyncImagePainter(Uri.EMPTY), //todo prendere da product
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    )
}