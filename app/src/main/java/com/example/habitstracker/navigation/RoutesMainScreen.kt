package com.example.habitstracker.navigation

sealed class RoutesMainScreen(
    val route: String,
) {
    object Main : RoutesMainScreen(route = "MainScreen")
    object AddHabit : RoutesMainScreen(route = "AddHabitScreen")
    object CreateNewHabit : RoutesMainScreen(route = "CreateOwnHabitScreen")
    object RepeatPicker : RoutesMainScreen(route = "RepeatPickerScreen")

}