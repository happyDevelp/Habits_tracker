package com.example.habitstracker.navigation.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItems (
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    BottomBarItems(
        label = "Today",
        icon = Icons.Default.DateRange,
        route = BottomBarScreens.TodayScreen.name
    ),
    BottomBarItems(
        label = "History",
        icon = Icons.Default.Home,
        route = BottomBarScreens.HistoryScreen.name
    ),
    BottomBarItems(
        label = "Me",
        icon = Icons.Default.AccountCircle,
        route = BottomBarScreens.MeScreen.name
    )
)