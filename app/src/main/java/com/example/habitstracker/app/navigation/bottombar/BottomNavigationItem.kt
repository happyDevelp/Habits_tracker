package com.example.habitstracker.app.navigation.bottombar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitstracker.R
import com.example.habitstracker.app.navigation.Route

data class BottomNavigationItem (
    @StringRes val titleResId: Int,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: Route
)

val listOfNavItems = listOf(
    BottomNavigationItem(
        titleResId = R.string.bottomnav_today,
        selectedIcon = Icons.Filled.DateRange,
        unSelectedIcon = Icons.Rounded.DateRange,
        route = Route.Today()
    ),
    BottomNavigationItem(
        titleResId = R.string.bottomnav_history,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Rounded.Home,
        route = Route.History()
    ),
    BottomNavigationItem(
        titleResId = R.string.bottomnav_statistic,
        selectedIcon = Icons.Filled.BarChart,
        unSelectedIcon = Icons.Rounded.BarChart,
        route = Route.Statistic
    ),
    BottomNavigationItem(
        titleResId = R.string.bottomnav_me,
        selectedIcon = Icons.Filled.AccountCircle,
        unSelectedIcon = Icons.Rounded.AccountCircle,
        route = Route.Profile
    )
)