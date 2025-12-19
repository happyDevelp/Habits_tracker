package com.example.habitstracker.habit.presentation.today_main.utility

import com.example.habitstracker.R

enum class AchievementSection(val titleResId: Int) {
    // Linking the resource ID to the Enum element
    HABITS_FINISHED(R.string.achiev_habits_finished),
    BEST_STREAK(R.string.achiev_best_streak),
    PERFECT_DAYS(R.string.achiev_perfect_days);

    companion object {
        // Context is no longer needed here because we parse technical names
        fun fromString(section: String): AchievementSection {
            return when (section) {
                //There should be exactly those lines that come from your DB/Logic
                "Habits Finished" -> HABITS_FINISHED
                "Best Streak" -> BEST_STREAK
                "Perfect Days" -> PERFECT_DAYS

                // Additional protection: if suddenly a translated line arrives,
                // you can try to find it among resources (optional),
                // But it is better to keep logic on English keys.
                else -> throw IllegalArgumentException("Invalid section: $section")
            }
        }
    }
}