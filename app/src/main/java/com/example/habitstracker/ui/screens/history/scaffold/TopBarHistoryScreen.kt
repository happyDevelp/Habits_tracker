package com.example.habitstracker.ui.screens.history.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.ui.custom.MyText
import com.example.habitstracker.ui.theme.screensBackgroundDark
import com.example.habitstracker.ui.theme.screenContainerBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHistoryScreen() {
    TopAppBar(
        title = { MyText(text = stringResource(id = R.string.history), textSize = 26.sp) },

        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share results",
                    tint = Color.White
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screensBackgroundDark
        )
    )
}