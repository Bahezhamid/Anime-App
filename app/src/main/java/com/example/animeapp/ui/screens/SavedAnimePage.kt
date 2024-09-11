package com.example.animeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animeapp.AnimeBottomAppBar
import com.example.animeapp.AnimeTopAppBar
import com.example.animeapp.R

@Composable
fun SavedAnimePage (
    onHomeClicked : () -> Unit,
    onBookClicked : () -> Unit,
    onProfileClicked : () -> Unit
) {
    Scaffold(
        topBar = {
            AnimeTopAppBar(title = "My List")
        },
        bottomBar = {
            AnimeBottomAppBar(
                onHomeClick = onHomeClicked,
                onSavedClick = {},
                onBookClick = onBookClicked,
                onProfileClick = onProfileClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
                .padding(start = 26.dp, end = 26.dp, top = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
                Image(
                    painter = painterResource(id = R.drawable.jiraya_pic),
                    contentDescription = "List is Empty",
                    modifier = Modifier
                        .height(220.dp)
                        .width(280.dp)
                        .clip(CircleShape)
                )
            Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Many shows to watch.",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = "Let's  watch some..",
                    style = MaterialTheme.typography.bodyLarge
                    )
            Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { /*TODO*/ },
                    
                    modifier = Modifier
                        .height(50.dp)
                        .width(184.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                    Text(
                        text = "Create new list",
                        style = MaterialTheme.typography.bodyLarge
                        )
                }
            
        }
    }
}