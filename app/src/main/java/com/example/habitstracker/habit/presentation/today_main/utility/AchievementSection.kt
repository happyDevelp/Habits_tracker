package com.example.habitstracker.habit.presentation.today_main.utility

import android.content.Context
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText

enum class AchievementSection() {
    HABITS_FINISHED, BEST_STREAK, PERFECT_DAYS;

    companion object {
        fun fromString(section: String, context: Context) = when (section) {
            UiText.StringResources(R.string.achiev_habits_finished)
                .asString(context) -> HABITS_FINISHED

            UiText.StringResources(R.string.achiev_best_streak).asString(context) -> BEST_STREAK
            UiText.StringResources(R.string.achiev_perfect_days).asString(context) -> PERFECT_DAYS
            else -> throw IllegalArgumentException("Invalid section: $section")
        }
    }
}