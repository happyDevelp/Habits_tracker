package com.example.habitstracker.navigation.bottombar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.habitstracker.ui.theme.PoppinsFontFamily

@Composable
fun NavigationBottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    BottomAppBar {
        listOfNavItems.forEach { bottomBarItem: BottomBarItems ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == bottomBarItem.route
                } == true,

                onClick = {
                    navController.navigate(bottomBarItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = bottomBarItem.icon, contentDescription = null)
                },
                label = {
                    Text(
                        text = bottomBarItem.label,
                        fontSize = 12.sp,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White.copy(0.7f)
                    )
                }
            )
        }
    }
}
