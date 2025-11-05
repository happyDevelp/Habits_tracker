package com.example.habitstracker.statistic.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitstracker.core.presentation.theme.HabitColor

object SettingsButtons {
    val list = listOf(
        SettingsButtonItem(
            text = "Account",
            icon = Icons.Outlined.AccountCircle,
            iconBackground = HabitColor.LeafGreen.dark,
        ),
        SettingsButtonItem(
            text = "Notifications",
            icon = Icons.Outlined.Notifications,
            iconBackground = HabitColor.Purple.light,
        ),
        SettingsButtonItem(
            text = "Language",
            icon = Icons.Outlined.Language,
            iconBackground = HabitColor.DeepBlue.dark,
        ),
        SettingsButtonItem(
            text = "Preferences",
            icon = Icons.Outlined.Tune,
            iconBackground = HabitColor.Rose.light,
        ),
        SettingsButtonItem(
            text = "Support",
            icon = Icons.Outlined.SupportAgent,
            iconBackground = HabitColor.Teal.light,
        ),
        SettingsButtonItem(
            text = "Rate Us",
            icon = Icons.Outlined.StarRate,
            iconBackground = HabitColor.Terracotta.light,
        ),
    )
}

data class SettingsButtonItem(
    val text: String,
    val icon: ImageVector,
    val iconBackground: Color,
)