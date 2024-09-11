package com.example.animeapp.ui.screens.AnimeChapterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.animeapp.R

@Composable
fun AnimeChaptersScreen() {
    Column {
       Row {
           Image(
               painter = painterResource(id = R.drawable.jujutsu_background),
               contentDescription = "Example Image",
           )
       }
    }
}