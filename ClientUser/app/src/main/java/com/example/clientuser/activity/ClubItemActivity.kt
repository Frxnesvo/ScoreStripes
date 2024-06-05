package com.example.clientuser.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.clientuser.model.Club
import com.example.clientuser.model.dto.ClubDto

@Composable
fun ClubItem(clubDto: ClubDto, onClick: () -> Unit){
    val club = Club.fromDto(clubDto)
    Image(
        painter = rememberAsyncImagePainter(club.image),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    )
}