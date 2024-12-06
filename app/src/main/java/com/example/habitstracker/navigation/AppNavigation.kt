package com.example.habitstracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.data.db.viewmodel.HabitViewModel
import com.example.habitstracker.navigation.bottombar.BottomBarScreens
import com.example.habitstracker.navigation.bottombar.NavigationBottomBar
import com.example.habitstracker.ui.screens.add_habit.AddHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.components.RepeatPicker
import com.example.habitstracker.ui.screens.edit_habit.EditHabitScreen
import com.example.habitstracker.ui.screens.edit_habit.components.EditRepeatPicker
import com.example.habitstracker.ui.screens.edit_habit.components.EditRepeatPickerScreen
import com.example.habitstracker.ui.screens.edit_habit.components.SelectedDay
import com.example.habitstracker.ui.screens.edit_habit.editViewModel.EditViewModel
import com.example.habitstracker.ui.screens.history.HistoryScreen
import com.example.habitstracker.ui.screens.me.MeScreen
import com.example.habitstracker.ui.screens.today_main.TodayScreen

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current
    val dbViewModel = hiltViewModel<HabitViewModel>()

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
                TodayScreen(viewModel = dbViewModel)
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
                route = "${RoutesMainScreen.CreateNewHabit.route}?param={param}",
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

            composable(                                                       /*&paramSelectedDays={paramSelectedDays}*/
                route = "${RoutesMainScreen.EditHabit.route}?paramId={paramId}",
                arguments = listOf(
                    navArgument("paramId") {
                        type = NavType.IntType
                        defaultValue = 0
                    },

                    )
            ) { backStackEntry ->
                val paramId = backStackEntry.arguments?.getInt("paramId") ?: 0
                val paramSelectedDays =
                    dbViewModel.habitsList.collectAsState().value.find { it.id == paramId }?.days
                        ?: "ERROR incorrect param (AppNavigation)"
                EditHabitScreen(
                    paramId = paramId,
                    selectedDaysParam = paramSelectedDays,
                    viewModel = dbViewModel
                )
            }

            composable(route = "${RoutesMainScreen.EditRepeatPicker.route}/{paramId}",
                arguments = listOf(
                    navArgument("paramId") {
                        type = NavType.IntType // Тип аргументу (Int, String, etc.)
                    }
                )
            ) { backStackEntry ->
                val paramId = backStackEntry.arguments?.getInt("paramId") ?: throw IllegalArgumentException("paramId is required")
                val result =
                    navController.previousBackStackEntry?.savedStateHandle?.get<MutableList<SelectedDay>>(
                        "param_selectedDays"
                    )
                EditRepeatPickerScreen(
                    paramId = paramId,
                    viewModel = dbViewModel,
                    _selectedDaysParam = result ?: listOf<SelectedDay>(
                        SelectedDay(true, "Mon"),
                        SelectedDay(true, "Tue"),
                        SelectedDay(true, "Wed"),
                        SelectedDay(true, "Thu"),
                        SelectedDay(true, "Fri"),
                        SelectedDay(true, "San"),
                        SelectedDay(true, "Sut"),
                    )
                )

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