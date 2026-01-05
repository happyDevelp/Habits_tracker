package com.olesmalysh.habitstracker.statistic.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.olesmalysh.habitstracker.R
import com.olesmalysh.habitstracker.app.navigation.Route
import com.olesmalysh.habitstracker.core.presentation.UiText
import com.olesmalysh.habitstracker.core.presentation.theme.HabitColor

object SettingsButtons {

    val list = listOf(
        SettingsButtonItem(
            text = UiText.StringResources(R.string.notifications),
            icon = Icons.Outlined.Notifications,
            iconBackground = HabitColor.Purple.light,
            route = Route.Settings.Notifications
        ),
        SettingsButtonItem(
            text = UiText.StringResources(R.string.language),
            icon = Icons.Outlined.Language,
            iconBackground = HabitColor.DeepBlue.dark,
            route = Route.Settings.Language
        ),
/*        SettingsButtonItem(
            text = "Preferences",
            icon = Icons.Outlined.Tune,
            iconBackground = HabitColor.Rose.light,
            route = Route.Settings.Preferences
        ),*/
        SettingsButtonItem(
            text = UiText.StringResources(R.string.support),
            icon = Icons.Outlined.SupportAgent,
            iconBackground = HabitColor.Teal.light,
            route = Route.Settings.Support
        ),
        SettingsButtonItem(
            text = UiText.StringResources(R.string.rate_us),
            icon = Icons.Outlined.StarRate,
            iconBackground = HabitColor.Terracotta.light,
            route = Route.Settings.RateUs
        ),
    )
}

data class SettingsButtonItem(
    val text: UiText,
    val icon: ImageVector,
    val iconBackground: Color,
    val route: Route
)