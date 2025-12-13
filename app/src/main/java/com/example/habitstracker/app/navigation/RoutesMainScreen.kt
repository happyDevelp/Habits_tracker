package com.example.habitstracker.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object BottomBarGraph : Route

    /*@Serializable
    data object HabitGraph: Route*/

    @Serializable
    data class Today(val historyDate: String? = null) : Route

    @Serializable
    data class History(val startTab: Int = 0) : Route

    @Serializable
    data object Statistic : Route

    @Serializable
    data object Me : Route

    object Settings {
        @Serializable
        data object Account : Route

        @Serializable
        data object Notifications : Route

        @Serializable
        data object Language : Route

        @Serializable
        data object Preferences : Route

        @Serializable
        data object Support : Route

        @Serializable
        data object RateUs : Route
    }


    @Serializable
    data object AddHabit : Route

    @Serializable
    data class GroupHabit(val groupName: String, val groupDescribe: String) : Route

    @Serializable
    data class CreateHabit(
        val id: Int? = null,
        val name: String,
        val icon: String,
        val iconColor: String,
        val isEditMode: Boolean = false
    ) : Route
}