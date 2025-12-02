package com.example.habitstracker.me.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeTopBar(notificationBoxOpen: () -> Unit) {
    TopAppBar(
        title = {
            MyText(
                text = "Me",
                textSize = 26.sp,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = screenBackgroundDark
        ),
        actions = {
            IconButton(onClick = { notificationBoxOpen() }) {
                Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Notifications",
                    tint = Color.White)
            }
        }
    )
}