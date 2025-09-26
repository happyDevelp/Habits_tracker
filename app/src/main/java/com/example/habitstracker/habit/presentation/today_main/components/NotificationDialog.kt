package com.example.habitstracker.habit.presentation.today_main.components

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.history.domain.AchievementEntity

data class UnlockedAchievement(
    /*@StringRes */val iconRes: Int,
    val target: Int,
    val description: String,
    val textPadding: Dp
)

object AchievementMetadata {
    val icons: (section: String, context: Context) -> Int = { section, context ->
        when (section) {
            UiText.StringResources(R.string.achiev_habits_finished)
                .asString(context) -> R.drawable.dart_board

            UiText.StringResources(R.string.achiev_perfect_days)
                .asString(context) -> R.drawable.calendar_hexagon

            UiText.StringResources(R.string.achiev_best_streak)
                .asString(context) -> R.drawable.streak_achiev

            else -> R.drawable.error_svg
        }
    }

    val description: (achievement: AchievementEntity, context: Context) -> String =
        { ach, context ->
            when (ach.section) {
                UiText.StringResources(R.string.achiev_habits_finished).asString(context) ->
                    UiText.StringResources(R.string.achDescription_finishHabitTimes, ach.target)
                        .asString(context)

                UiText.StringResources(R.string.achiev_perfect_days).asString(context) ->
                    UiText.StringResources(R.string.achDescription_perfectDays, ach.target)
                        .asString(context)

                UiText.StringResources(R.string.achiev_best_streak).asString(context) ->
                    UiText.StringResources(R.string.achDescription_daysStreak, ach.target)
                        .asString(context)

                else -> "ErROR on Notification Dialog"
            }
        }

    val textPadding: (section: String, context: Context) -> Dp = { section, context ->
        when (section) {
            UiText.StringResources(R.string.achiev_habits_finished).asString(context) -> 16.dp
            UiText.StringResources(R.string.achiev_perfect_days).asString(context) -> 57.dp
            UiText.StringResources(R.string.achiev_best_streak).asString(context) -> 0.dp
            else -> 0.dp
        }
    }
}

