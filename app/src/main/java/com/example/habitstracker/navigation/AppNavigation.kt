package com.example.habitstracker.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.habitstracker.app.LocalNavController
import com.example.habitstracker.data.db.viewmodel.HabitViewModel
import com.example.habitstracker.navigation.bottombar.NavigationBottomBar
import com.example.habitstracker.ui.screens.add_habit.AddHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.CreateOwnHabitScreen
import com.example.habitstracker.ui.screens.create_own_habit.components.RepeatPicker
import com.example.habitstracker.ui.screens.edit_habit.EditHabitScreen
import com.example.habitstracker.ui.screens.edit_habit.components.EditRepeatPickerScreen
import com.example.habitstracker.ui.screens.history.HistoryScreen
import com.example.habitstracker.ui.screens.me.MeScreen
import com.example.habitstracker.ui.screens.today_main.TodayScreen

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current
    val dbViewModel = hiltViewModel<HabitViewModel>()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = getBottomBarState(navBackStackEntry)

    val density = LocalDensity.current

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                NavigationBottomBar(navController)
            }
        }
    ) { paddingValues ->


        NavHost(
            navController = navController,
            startDestination = Route.BottomBarGraph,
            modifier = Modifier.padding(paddingValues)
        ) {
            navigation<Route.BottomBarGraph>(
                startDestination = Route.Today
            ) {
                composable<Route.Today> {
                    TodayScreen(viewModel = dbViewModel)
                }

                composable<Route.History> {
                    HistoryScreen()
                }

                composable<Route.Me> {
                    MeScreen()
                }
            }

            composable<Route.AddHabit> {
                AddHabitScreen()
            }

            composable<Route.CreateHabit>
            { backStackEntry ->
                val args = backStackEntry.toRoute<Route.CreateHabit>()
                CreateOwnHabitScreen(param = args.param ?: "Everyday teststststs")
            }

            composable<Route.RepeatPicker> {
                RepeatPicker()
            }

            composable<Route.EditHabit> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.EditHabit>()
                EditHabitScreen(
                    paramId = args.id,
                    viewModel = dbViewModel
                )
            }

            composable<Route.EditRepeatPicker> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.EditRepeatPicker>()
                EditRepeatPickerScreen(paramId = args.id, viewModel = dbViewModel)
            }
        }
    }
}

fun getBottomBarState(navBackStackEntry: NavBackStackEntry?): Boolean {
    val baseRouteName = "com.example.habitstracker.navigation.Route."
    val currentRoute = navBackStackEntry?.destination?.route

    return when (currentRoute) {
        baseRouteName + Route.Today -> true
        baseRouteName + Route.History -> true
        baseRouteName + Route.Me -> true

        else -> false
    }
}