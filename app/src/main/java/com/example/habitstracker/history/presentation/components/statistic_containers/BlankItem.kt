package com.example.habitstracker.history.presentation.components.statistic_containers

import android.content.Context
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.HabitColor
import com.example.habitstracker.core.presentation.utils.gradientColor

data class BlankItem(
    val width: Dp,
    val gradientColor: Brush,
    val topText: String,
    val middleText: String,
    val bottomText: String
)

fun getFilledBlankList(
    context: Context,
    currentStreak: Int,
    bestStreak: Int,
    totalCompletedHabits: Int,
    thisWeekSelectedHabits: Int,
    totalHabits: Int,
    percentage: Float,
    perfectDaysCounter: Int,
    thisWeekPerfectedDays: Int
): List<BlankItem> {
    val listOf = listOf(
        BlankItem(
            width = 140.dp,
            gradientColor = gradientColor(HabitColor.DeepBlue.light, HabitColor.DeepBlue.dark),
            topText = context.getString(R.string.current_streak),
            middleText = "$currentStreak",
            bottomText = "The best streak: $bestStreak",
        ),
        BlankItem(
            width = 125.dp,
            gradientColor = gradientColor(HabitColor.BrickRed.light, HabitColor.BrickRed.dark),
            topText = context.getString(R.string.number_of_completed_habits),
            middleText = "$totalCompletedHabits",
            bottomText = "This week: $thisWeekSelectedHabits"
        ),
        BlankItem(
            width = 130.dp,
            gradientColor = gradientColor(HabitColor.Orange.light, HabitColor.Orange.dark),
            topText = context.getString(R.string.percentage_of_completed_habits),
            middleText = "${percentage.toInt()}%",
            bottomText = "Habits: $totalCompletedHabits/$totalHabits",
        ),
        BlankItem(
            width = 130.dp,
            gradientColor = gradientColor(HabitColor.LeafGreen.light, HabitColor.LeafGreen.dark),
            topText = context.getString(R.string.perfect_days),
            middleText = "$perfectDaysCounter",
            bottomText = "This week: $thisWeekPerfectedDays",
        )
    )
    return listOf
}
