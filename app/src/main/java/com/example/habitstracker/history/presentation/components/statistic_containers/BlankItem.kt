package com.example.habitstracker.history.presentation.components.statistic_containers

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.blueColor
import com.example.habitstracker.core.presentation.theme.greenColor
import com.example.habitstracker.core.presentation.theme.orangeColor
import com.example.habitstracker.core.presentation.theme.redColor

data class BlankItem(
    val width: Dp,
    val color: Color,
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
            color = blueColor,
            topText = context.getString(R.string.current_streak),
            middleText = "$currentStreak",
            bottomText = "The best streak: $bestStreak",
        ),

        BlankItem(
            width = 125.dp,
            color = redColor,
            topText = context.getString(R.string.number_of_completed_habits),
            middleText = "$totalCompletedHabits",
            bottomText = "This week: $thisWeekSelectedHabits"
        ),

        BlankItem(
            width = 130.dp,
            color = orangeColor,
            topText = context.getString(R.string.percentage_of_completed_habits),
            middleText = "${percentage.toInt()}%",
            bottomText = "Habits: $totalCompletedHabits/$totalHabits",
        ),

        BlankItem(
            width = 130.dp,
            color = greenColor,
            topText = context.getString(R.string.perfect_days),
            middleText = "$perfectDaysCounter",
            bottomText = "This week: $thisWeekPerfectedDays",
        )
    )
    return listOf
}