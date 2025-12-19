package com.example.habitstracker.habit.presentation.today_main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.R
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.app.LocalSettingsSheetController
import com.example.habitstracker.app.SettingsSheetController
import com.example.habitstracker.core.presentation.theme.AppTheme
import com.example.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.example.habitstracker.core.presentation.theme.containerBackgroundDark
import com.example.habitstracker.statistic.presentation.components.SettingsButtons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSheet(sheetState: SheetState) {
    val navController = LocalNavController.current
    val settingsController = LocalSettingsSheetController.current
    ModalBottomSheet(
        onDismissRequest = { settingsController.close() },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.615f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { settingsController.close() }) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_settings),
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(R.string.settings),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = PoppinsFontFamily
                )
            }
            Spacer(Modifier.height(10.dp))

            val buttonsList = SettingsButtons.list
            buttonsList.forEach { button ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            settingsController.close()
                            navController.navigate(button.route)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = containerBackgroundDark)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = button.iconBackground),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = button.icon,
                                contentDescription = button.text.asString(),
                                tint = Color.White
                            )
                        }
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = button.text.asString(),
                            fontSize = 17.sp,
                            color = Color.White,
                            fontFamily = PoppinsFontFamily
                        )
                    }
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 14.dp)
                            .size(30.dp),
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    val mockSettingsSheetController = SettingsSheetController()
    CompositionLocalProvider(
        LocalNavController provides mockNavController,
        LocalSettingsSheetController provides mockSettingsSheetController
        ) {
        AppTheme(darkTheme = true) {
            SettingsSheet(
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            )
        }
    }
}
