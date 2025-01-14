package com.example.habitstracker.navigation.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitstracker.navigation.Route

data class BottomBarItems (
    val label: String,
    val icon: ImageVector,
    val route: Route
)

val listOfNavItems = listOf(
    BottomBarItems(
        label = "Today",
        icon = Icons.Default.DateRange,
        route = Route.Today
    ),
    BottomBarItems(
        label = "History",
        icon = Icons.Default.Home,
        route = Route.History
    ),
    BottomBarItems(
        label = "Me",
        icon = Icons.Default.AccountCircle,
        route = Route.Me
    )
)