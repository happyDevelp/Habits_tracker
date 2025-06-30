package com.example.habitstracker.app.navigation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.habitstracker.app.navigation.bottombar.NavigationBottomBar
import com.example.habitstracker.habit.presentation.add_habit.AddHabitScreen
import com.example.habitstracker.habit.presentation.create_own_habit.CreateOwnHabitRoot
import com.example.habitstracker.habit.presentation.create_own_habit.components.RepeatPicker
import com.example.habitstracker.habit.presentation.detail_habit.DetailHabitScreen
import com.example.habitstracker.habit.presentation.edit_habit.EditHabitRoot
import com.example.habitstracker.habit.presentation.edit_habit.components.EditRepeatPickerRoot
import com.example.habitstracker.habit.presentation.today_main.MainScreenViewModel
import com.example.habitstracker.habit.presentation.today_main.TodayScreenRoot
import com.example.habitstracker.history.presentation.HistoryScreen
import com.example.habitstracker.profile.presentation.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current

    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = getBottomBarState(navBackStackEntry)

    val density = LocalDensity.current

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val changeSelectedItemState: (index: Int) -> Unit = { index ->
        selectedItemIndex = index
    }

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
                NavigationBottomBar(
                    navController,
                    selectedItemIndex,
                    changeSelectedItemState
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.BottomBarGraph,
            modifier = Modifier.padding(paddingValues)
        ) {
            navigation<Route.BottomBarGraph>(
                startDestination = Route.Today()
            ) {
                composable<Route.Today> { backStackEntry ->
                    val args = backStackEntry.toRoute<Route.Today>()
                    TodayScreenRoot(
                        viewModel = mainScreenViewModel,
                        historyDate = args.historyDate
                    )
                }

                composable<Route.History> {
                    HistoryScreen(changeSelectedItemState = changeSelectedItemState)
                }

                composable<Route.Profile> {
                    ProfileScreen()
                }
            }

            composable<Route.AddHabit> {
                AddHabitScreen()
            }

            composable<Route.GroupHabit> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.GroupHabit>()
                DetailHabitScreen(groupName = args.groupName, groupDescribe = args.groupDescribe)
            }

            composable<Route.CreateHabit> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.CreateHabit>()
                CreateOwnHabitRoot(
                    name = args.name,
                    icon = args.icon,
                    iconColor = args.iconColor,
                    viewModel = mainScreenViewModel
                )
            }

            composable<Route.RepeatPicker> {
                RepeatPicker()
            }

            composable<Route.EditHabit> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.EditHabit>()
                EditHabitRoot(
                    paramId = args.id,
                    viewModel = mainScreenViewModel
                )
            }

            composable<Route.EditRepeatPicker> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.EditRepeatPicker>()
                EditRepeatPickerRoot(
                    paramId = args.id,
                    viewModel = mainScreenViewModel
                )
            }
        }
    }
}

fun getBottomBarState(navBackStackEntry: NavBackStackEntry?): Boolean {
    val baseRouteName = "com.example.habitstracker.app.navigation.Route."
    val currentRoute = navBackStackEntry?.destination?.route

    return when (currentRoute) {
        //"Today?historyDate={historyDate}"
        baseRouteName + "Today?historyDate={historyDate}" -> true
        baseRouteName + Route.History -> true
        baseRouteName + Route.Profile -> true

        else -> false
    }
}
//com.example.habitstracker.app.navigation.Route.Today?historyDate={historyDate}
//com.example.habitstracker.app.navigation.Route.Today?historyDate={historyDate}
//com.example.habitstracker.app.navigation.Route.Today?historyDate={historyDate}