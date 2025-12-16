package com.example.habitstracker.me.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalSettingsSheetController
import com.example.habitstracker.core.presentation.MyText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeTopBar(
    notificationBoxOpen: () -> Unit,
    badgeCount: Int,
) {
    val settingsController = LocalSettingsSheetController.current
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
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.size(27.dp))
                {
                    IconButton(onClick = { notificationBoxOpen() }) {
                        Icon(
                            imageVector = Icons.Rounded.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White
                        )
                    }

                    if (badgeCount > 0) {
                        Badge(
                            modifier = Modifier
                                .size(10.dp)
                                .align(Alignment.TopEnd),
                            containerColor = Color.Red,
                            content = {}
                        )
                    }
                }

                IconButton(
                    onClick = {
                        settingsController.open()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings),
                    )
                }

            }
        }
    )
}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {

    MeTopBar(notificationBoxOpen = {}, 2)
}