package com.example.habitstracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.navigation.bottombar.BottomBarScreens
import com.example.habitstracker.navigation.bottombar.NavigationBottomBar
import com.example.habitstracker.ui.screens.add_habit.AddHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.components.RepeatPicker
import com.example.habitstracker.ui.screens.history.HistoryScreen
import com.example.habitstracker.ui.screens.me.MeScreen
import com.example.habitstracker.ui.screens.today_main.TodayScreen

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = getBottomBarState(navBackStackEntry)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBottomBar(currentDestination, navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomBarScreens.TodayScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = BottomBarScreens.TodayScreen.name) {
                TodayScreen()
            }
            composable(route = BottomBarScreens.HistoryScreen.name) {
                HistoryScreen()
            }
            composable(route = BottomBarScreens.MeScreen.name) {
                MeScreen()
            }

            composable(route = RoutesMainScreen.AddHabit.route) {
                AddHabitScreen()
            }

            composable(
                route = "CreateOwnHabitScreen?param={param}",
                arguments = listOf(
                    navArgument("param") {
                        type = NavType.StringType
                        defaultValue = "Everyday"
                    })
            ) { backStackEntry ->
                val param = backStackEntry.savedStateHandle.get<String>("param") ?: "Everyday"
                CreateOwnHabitScreen(param = param)
            }

            composable(route = RoutesMainScreen.RepeatPicker.route) {
                RepeatPicker()
            }

        }
    }
}

fun getBottomBarState(navBackStackEntry: NavBackStackEntry?): Boolean {
    return when (navBackStackEntry?.destination?.route) {
        RoutesMainScreen.RepeatPicker.route -> false
        RoutesMainScreen.AddHabit.route -> false
        RoutesMainScreen.CreateNewHabit.route -> false

        else -> true
    }
}