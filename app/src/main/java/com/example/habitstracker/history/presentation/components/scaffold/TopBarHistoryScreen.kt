package com.example.habitstracker.history.presentation.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalSettingsSheetController
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHistoryScreen() {
    val settingsController = LocalSettingsSheetController.current

    TopAppBar(
        title = { MyText(text = stringResource(id = R.string.history), textSize = 26.sp) },

        actions = {
            IconButton(onClick = { settingsController.open() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackgroundDark
        )
    )
}