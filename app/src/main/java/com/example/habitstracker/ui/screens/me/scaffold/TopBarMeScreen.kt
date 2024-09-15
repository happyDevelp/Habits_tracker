package com.example.habitstracker.ui.screens.me.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.habitstracker.ui.custom.MyText
import com.example.habitstracker.ui.theme.screensBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMeScreen(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { MyText(text = "MeScreen", textSize = 26.sp) },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screensBackgroundDark
        )
    )
}