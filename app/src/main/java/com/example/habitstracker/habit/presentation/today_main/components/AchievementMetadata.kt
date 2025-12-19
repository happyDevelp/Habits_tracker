package com.example.habitstracker.habit.presentation.today_main.components

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.history.domain.AchievementEntity
object AchievementMetadata {
    // Context is not needed for the condition here, but we leave it in the signature so as not to break the calls
    val icons: (section: String, context: Context) -> Int = { section, _ ->
        when (section) {
            // Checking technical keys
            "Habits Finished" -> R.drawable.dart_board
            "Perfect Days" -> R.drawable.calendar_hexagon
            "Best Streak" -> R.drawable.streak_achiev
            else -> R.drawable.error_svg
        }
    }

    val description: (achievement: AchievementEntity, context: Context) -> String =
        { ach, context ->
            when (ach.section) {
                // Condition: check the English name
                "Habits Finished" ->
                    // Result: return the translated text (context is needed here)
                    UiText.StringResources(R.string.achDescription_finishHabitTimes, ach.target)
                        .asString(context)

                "Perfect Days" ->
                    UiText.StringResources(R.string.achDescription_perfectDays, ach.target)
                        .asString(context)

                "Best Streak" ->
                    UiText.StringResources(R.string.achDescription_daysStreak, ach.target)
                        .asString(context)

                else -> "ErROR on Notification Dialog"
            }
        }

    val textPadding: (section: String, context: Context) -> Dp = { section, _ ->
        when (section) {
            "Habits Finished" -> 16.dp
            "Perfect Days" -> 57.dp
            "Best Streak" -> 0.dp
            else -> 0.dp
        }
    }
}