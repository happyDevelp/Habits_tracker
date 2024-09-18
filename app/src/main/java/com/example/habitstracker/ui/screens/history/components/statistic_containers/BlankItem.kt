package com.example.habitstracker.ui.screens.history.components.statistic_containers

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.habitstracker.R
import com.example.habitstracker.ui.theme.blueColor
import com.example.habitstracker.ui.theme.greenColor
import com.example.habitstracker.ui.theme.redColor
import com.example.habitstracker.ui.theme.yellowColor


data class BlankItem(
    val width: Dp,
    val color: Color,
    val topText: String,
    val middleText: String,
    val bottomText: String,
)

fun getFilledBlankList(context: Context): List<BlankItem> {
    val listOf = listOf(
        BlankItem(
            width = 140.dp,
            color = blueColor,
            topText = context.getString(R.string.current_streak),
            middleText = "0",
            bottomText = "The best streak of success: ",
        ),

        BlankItem(
            width = 125.dp,
            color = redColor,
            topText = context.getString(R.string.number_of_completed_habits),
            middleText = "48",
            bottomText = "This week: ",
        ),

        BlankItem(
            width = 130.dp,
            color = yellowColor,
            topText = context.getString(R.string.percentage_of_completed_habits),
            middleText = "20",
            bottomText = "Habits: 48/242",
        ),

        BlankItem(
            width = 130.dp,
            color = greenColor,
            topText = context.getString(R.string.perfect_days),
            middleText = "7",
            bottomText = "This week: ",
        )
    )
    return listOf
}