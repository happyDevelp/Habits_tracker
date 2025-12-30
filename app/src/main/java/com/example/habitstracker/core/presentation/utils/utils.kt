package com.example.habitstracker.core.presentation.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.habit.domain.ShownHabit
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun List<LocalDate>.chunked(size: Int): List<List<LocalDate>> {
    return this.withIndex().groupBy { it.index / size }.values.map { it.map { it.value } }
}

fun generateDateSequence(startDate: LocalDate, daysCount: Int): List<LocalDate> {
    return List(daysCount) { startDate.plusDays(it.toLong()) }
}

fun Modifier.clickWithRipple(
    color: Color = Color.White,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }

    this
        .clickable(
            interactionSource = interactionSource,
            indication = ripple()
        ) {
            onClick()
        }
}

fun Color.toHex(): String {
    val argb = this.toArgb()
    return String.format("#%08X", argb)
}

fun String.getColorFromHex(): Color =
    Color(this.toColorInt())

fun getIconName(icon: ImageVector): String {
    return icon.name.split(".")[1]
}

fun iconByName(name: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

val shownHabitExample1 = ShownHabit(0, 0, "habit example 1", "SentimentVerySatisfied",
    HabitColor.SkyBlue.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample2 = ShownHabit(1, 0,"habit example 2", "SentimentVerySatisfied",
    HabitColor.Orange.light.toHex(), "Everyday", "Anytime", false, false)

val shownHabitExample3 = ShownHabit(2, 0,"habit example 3", "SentimentVerySatisfied",
    HabitColor.LeafGreen.light.toHex(), "Everyday", "Evening", false, false)

fun getGradientByLightColor(lightColor: Color) = when (lightColor) {
    HabitColor.SkyBlue.light -> HabitColor.SkyBlue
    HabitColor.LeafGreen.light -> HabitColor.LeafGreen
    HabitColor.Amber.light -> HabitColor.Amber
    HabitColor.DeepBlue.light -> HabitColor.DeepBlue
    HabitColor.BrickRed.light -> HabitColor.BrickRed
    HabitColor.Cyan.light -> HabitColor.Cyan
    HabitColor.Orange.light -> HabitColor.Orange
    HabitColor.Teal.light -> HabitColor.Teal
    HabitColor.Golden.light -> HabitColor.Golden
    HabitColor.Lime.light -> HabitColor.Lime
    HabitColor.Aqua.light -> HabitColor.Aqua
    HabitColor.Purple.light -> HabitColor.Purple
    HabitColor.Terracotta.light -> HabitColor.Terracotta
    HabitColor.Rose.light -> HabitColor.Rose
    HabitColor.DarkGreen.light -> HabitColor.DarkGreen
    HabitColor.Sand.light -> HabitColor.Sand

    else -> HabitColor.DefaultColor // fallback
}

fun gradientColor(lightColor: Color, darkColor: Color, radius: Float = 600f): Brush = Brush.radialGradient(
    listOf(lightColor, darkColor),
    center = Offset(50f, 20f),
    radius = radius // Distribution radius
)

fun Long.toFormattedDate(): String {
    val instant = Instant.ofEpochMilli(this)

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())

    return instant.atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}

fun calculateInitialDelay(): Long {
    val now = LocalDateTime.now()

    // Set the time when we want to send the notification (for example, 21:00)
    val targetTime = LocalTime.of(11, 11)
    var targetDateTime = now.with(targetTime)

    // If 21:00 today has already passed, we plan for tomorrow
    if (now.isAfter(targetDateTime)) {
        targetDateTime = targetDateTime.plusDays(1)
    }

    // Calculating the difference in milliseconds
    return Duration.between(now, targetDateTime).toMillis()
}

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current

    // 1.Status for displaying an explanatory dialog
    var showExplanationDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Permission received, do nothing or update any settings
            } else {
                // 2. The user refused -> we show our window
                showExplanationDialog = true
            }
        }
    )

    // The logic of launching a request at startup
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                launcher.launch(permission)
            }
        }
    }

    // 3.The dialog itself. It will only appear if showExplanationDialog == true
    if (showExplanationDialog) {
        AlertDialog(
            onDismissRequest = { showExplanationDialog = false },
            title = {
                Text(text = stringResource(R.string.notifications_are_important)) // Можна використати stringResource
            },
            text = {
                Text(text = "Without this permission, we will not be able to remind you to complete your habits. Please enable notifications in settings.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExplanationDialog = false
                        // Відкриваємо налаштування саме нашого додатку
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text(stringResource(R.string.settings))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExplanationDialog = false }) {
                    Text(stringResource(R.string.no_thanks))
                }
            }
        )
    }
}