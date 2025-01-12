package com.example.habitstracker.navigation

import kotlinx.serialization.Serializable

sealed class RoutesMainScreen(
    val route: String,
) {

    object Main : RoutesMainScreen(route = "MainScreen")

    object AddHabit : RoutesMainScreen(route = "AddHabitScreen")
    object CreateNewHabit : RoutesMainScreen(route = "CreateOwnHabitScreen")
    object RepeatPicker : RoutesMainScreen(route = "RepeatPickerScreen")
    object EditHabit: RoutesMainScreen(route = "EditHabitScreen")
    object EditRepeatPicker : RoutesMainScreen(route = "EditRepeatPickerScreen")

}

sealed interface Route {

    @Serializable
    data object Today: Route

    @Serializable
    data object History: Route

    @Serializable
    data object Me: Route

    @Serializable
    data object AddHabit: Route

    @Serializable
    data class CreateHabit(val param: String): Route

    @Serializable
    data object RepeatPicker: Route

    @Serializable
    data class EditHabit(val id: Int): Route

    @Serializable
    data class EditRepeatPicker(val id: Int): Route


}