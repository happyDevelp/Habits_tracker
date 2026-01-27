package com.olesmalysh.habitstracker.core.presentation.settings_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.olesmalysh.habitstracker.R
import com.olesmalysh.habitstracker.app.LocalNavController
import com.olesmalysh.habitstracker.core.notification.presentation.NotificationUiState
import com.olesmalysh.habitstracker.core.notification.presentation.NotificationViewModel
import com.olesmalysh.habitstracker.core.presentation.theme.AppTheme
import com.olesmalysh.habitstracker.core.presentation.theme.PoppinsFontFamily
import com.olesmalysh.habitstracker.core.presentation.theme.screenBackgroundDark

@Composable
fun NotificationScreenRoot(
    notificationVM: NotificationViewModel = hiltViewModel<NotificationViewModel>()
) {
    val state by notificationVM.state.collectAsStateWithLifecycle()

    NotificationScreen(
        state = state,
        onToggle = notificationVM::onToggle,
        onTimeSelected = notificationVM::onTimeSelected
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    state: NotificationUiState,
    onToggle: (Boolean) -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {
    val navController = LocalNavController.current
    var showPicker by remember { mutableStateOf(false) }

    if (showPicker) {
        TimePickerDialogM3(
            initialHour = state.hour,
            initialMinute = state.minute,
            onConfirm = { h, m ->
                onTimeSelected(h, m)
                showPicker = false
            },
            onDismiss = { showPicker = false }
        )
    }

    Scaffold(
        modifier = modifier.padding(vertical = 8.dp),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Notification",
                            color = Color.White,
                            fontFamily = PoppinsFontFamily,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },

                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = screenBackgroundDark
                ),
                actions = {
                    IconButton(onClick = { }) { }
                }
            )
        },
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(screenBackgroundDark),
            contentAlignment = Alignment.TopCenter
        ) {


            DailyReminderCard(
                modifier = Modifier.padding(top = 22.dp),
                enabled = state.enabled,
                timeText = state.timeText,
                onEnabledChange = { newValue ->
                    onToggle(newValue)
                },

                onTimeClick = {
                    showPicker = true
                }

                /*onTimeClick = {
                    // Create and show classic Android time picker dialog
                    android.app.TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            onTimeSelected(hour, minute)
                        },
                        state.hour, // initial hour
                        state.minute,  // initial minute
                        true // 24-hour format
                    ).show()
                },*/
            )


        }
    }
}

@Composable
fun DailyReminderCard(
    enabled: Boolean,
    timeText: String,
    onEnabledChange: (Boolean) -> Unit,
    onTimeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBg = Color(0xFF1D2838) // 0xFF1E2A3A
    val border = Color.White.copy(alpha = 0.10f)
    val textPrimary = Color.White.copy(alpha = 0.92f)
    val textSecondary = Color.White.copy(alpha = 0.60f)

    val pillBg = Color(0xFF162233)        // Slightly darker than card
    val pillBorder = Color.White.copy(alpha = 0.14f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(140.dp)
            // Outer shadow similar to the screenshot
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.40f),
                spotColor = Color.Black.copy(alpha = 0.40f)
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(1.dp, border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            // Top row: title/subtitle + switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.daily_reminders),
                        color = textPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.get_a_reminder_every_day),
                        color = textSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Switch(
                    modifier = Modifier.scale(0.85f),
                    checked = enabled,
                    onCheckedChange = onEnabledChange,
                    // Keep default colors if you already have theme; you can customize if needed
                )
            }

            Spacer(Modifier.height(14.dp))

            // Divider
            HorizontalDivider(
                color = Color.White.copy(alpha = 0.10f),
                thickness = 1.dp
            )

            Spacer(Modifier.height(14.dp))

            // Bottom row: reminder time text + time pill
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.alpha(if (enabled) 1f else 0.50f),
                        text = stringResource(R.string.reminder_time),
                        color = textPrimary,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Every day at $timeText",
                        color = textSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TimePill(
                    enabled = enabled,
                    timeText = timeText,
                    onClick = onTimeClick,
                    background = pillBg,
                    border = pillBorder,
                    contentColor = textPrimary
                )
            }
        }
    }
}

@Composable
private fun TimePill(
    enabled: Boolean,
    timeText: String,
    onClick: () -> Unit,
    background: Color,
    border: Color,
    contentColor: Color
) {
    Surface(
        onClick = onClick,
        enabled = enabled, // disables click + ripple
        color = background,
        contentColor = contentColor,
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, border.copy(alpha = if (enabled) border.alpha else 0.10f)),
        shadowElevation = if (enabled) 6.dp else 0.dp, // less depth when disabled
        tonalElevation = 0.dp,
        modifier = Modifier.alpha(if (enabled) 1f else 0.45f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timeText,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = if (enabled) 1f else 0.70f)
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Outlined.AccessTime,
                contentDescription = "Time",
                modifier = Modifier.size(18.dp),
                tint = contentColor.copy(alpha = if (enabled) 0.85f else 0.55f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogM3(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(state.hour, state.minute)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(state = state)
        }
    )
}

@Preview
@Composable
private fun Preview() {
    val mockNavController = rememberNavController()
    CompositionLocalProvider(value = LocalNavController provides mockNavController) {
        AppTheme(darkTheme = true) {
            NotificationScreen(
                state = NotificationUiState(),
                onToggle = {},
                onTimeSelected = { _, _ -> }
            )
        }
    }
}