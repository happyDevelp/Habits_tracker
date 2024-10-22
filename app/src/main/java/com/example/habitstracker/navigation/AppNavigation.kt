package com.example.habitstracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.navigation.bottombar.BottomBarItems
import com.example.habitstracker.navigation.bottombar.BottomBarScreens
import com.example.habitstracker.navigation.bottombar.listOfNavItems
import com.example.habitstracker.ui.screens.add_habit.AddHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitViewModel
import com.example.habitstracker.ui.screens.create_own_habit.components.RepeatPicker
import com.example.habitstracker.ui.screens.history.HistoryScreen
import com.example.habitstracker.ui.screens.me.MeScreen
import com.example.habitstracker.ui.screens.today_main.TodayScreen
import com.example.habitstracker.ui.theme.PoppinsFontFamily
import kotlin.reflect.typeOf

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var showBottomBar by remember {
        mutableStateOf(true)
    }

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        RoutesMainScreen.RepeatPicker.route -> false
        RoutesMainScreen.AddHabit.route -> false
        RoutesMainScreen.CreateNewHabit.route -> false

        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
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
                        defaultValue = "ADMIN CHECK CHICKSS"
                    })
            ) {backStackEntry ->
                val param = backStackEntry.arguments?.getString("param") ?: "Default param value"
                CreateOwnHabitScreen(param = param)
            }

            composable(route = RoutesMainScreen.RepeatPicker.route) {
                RepeatPicker()
            }

        }
    }
}