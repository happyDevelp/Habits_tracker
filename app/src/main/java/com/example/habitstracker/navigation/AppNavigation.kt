package com.example.habitstracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.habitstracker.navigation.bottombar.BottomBarItems
import com.example.habitstracker.navigation.bottombar.BottomBarScreens
import com.example.habitstracker.navigation.bottombar.listOfNavItems
import com.example.habitstracker.ui.screens.history.HistoryScreen
import com.example.habitstracker.ui.screens.main.MainScreen
import com.example.habitstracker.ui.screens.me.MeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

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
                        label = { Text(text = bottomBarItem.label) }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomBarScreens.TodayScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = BottomBarScreens.TodayScreen.name) {
                MainScreen()
            }
            composable(route = BottomBarScreens.HistoryScreen.name) {
                HistoryScreen()
            }
            composable(route = BottomBarScreens.MeScreen.name) {
                MeScreen()
            }
        }
    }
}