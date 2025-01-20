package com.example.habitstracker.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object BottomBarGraph: Route

    /*@Serializable
    data object HabitGraph: Route*/

    @Serializable
    data object Today: Route

    @Serializable
    data object History: Route

    @Serializable
    data object Profile: Route

    @Serializable
    data object AddHabit: Route

    @Serializable
    data class CreateHabit(val param: String?): Route

    @Serializable
    data object RepeatPicker: Route

    @Serializable
    data class EditHabit(val id: Int): Route

    @Serializable
    data class EditRepeatPicker(val id: Int): Route
}