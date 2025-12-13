package com.example.habitstracker.app.navigation.bottombar

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
import com.example.habitstracker.app.navigation.Route

data class BottomNavigationItem (
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: Route
)

val listOfNavItems = listOf(
    BottomNavigationItem(
        title = "Today",
        selectedIcon = Icons.Filled.DateRange,
        unSelectedIcon = Icons.Rounded.DateRange,
        route = Route.Today()
    ),
    BottomNavigationItem(
        title = "History",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Rounded.Home,
        route = Route.History()
    ),
    BottomNavigationItem(
        title = "Statistic",
        selectedIcon = Icons.Filled.BarChart,
        unSelectedIcon = Icons.Rounded.BarChart,
        route = Route.Statistic
    ),
    BottomNavigationItem(
        title = "Me",
        selectedIcon = Icons.Filled.AccountCircle,
        unSelectedIcon = Icons.Rounded.AccountCircle,
        route = Route.Me
    )
)