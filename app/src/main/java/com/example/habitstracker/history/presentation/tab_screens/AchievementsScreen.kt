package com.example.habitstracker.history.presentation.tab_screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitstracker.R
import com.example.habitstracker.core.presentation.theme.screensBackgroundDark
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.presentation.components.HabitBox
import java.time.LocalDate

@Preview(showSystemUi = true)
@Composable
fun AchievementsScreenPreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screensBackgroundDark)
    )
    {
        AchievementsScreen(mapHabitsToDate = mapOf())
    }
}

@Composable
fun AchievementsScreen(mapHabitsToDate: Map<LocalDate, List<DateHabitEntity>>) {
    val totalFinishedHabits = mapHabitsToDate

        .values
        .flatten()
        .count { it.isCompleted }

    val totalPerfectDays = mapHabitsToDate
        .values
        .count {
            it.all { habit -> habit.isCompleted } // if all habits are completed
        }

    val bestStreak = getBestStreak(mapHabitsToDate)

    val sections = listOf(
        // first section
        AchievementSection(
            title = stringResource(R.string.achiev_habits_finished),
            iconRes = R.drawable.dart_board,
            targets = listOf("1", "10", "25", "100", "500", "1000"),
            description = { target, index ->
                if (index == 0) "Finish Your Habit First Time"
                else "Finish Habit For The $target Times"
            },
            progress = totalFinishedHabits
        ),

        // second section
        AchievementSection(
            title = stringResource(R.string.achiev_perfect_days),
            iconRes = R.drawable.calendar_hexagon,
            targets = listOf("3", "10", "25", "50", "100", "250"),
            progress = totalPerfectDays,
            description = { target, index ->
                "$target Perfect Days"
            }
        ),

        //third section
        AchievementSection(
            title = stringResource(R.string.achiev_best_streak),
            iconRes = R.drawable.streak_achiev,
            targets = listOf("3", "5", "10", "20", "50", "100"),
            progress = bestStreak,
            description = { target, index ->
                "$target Days Streak"
            }
        ),
    )

    LazyColumn {
        items(sections.size) { index ->
            HabitBox(section = sections[index])
        }
    }
}

private fun getBestStreak(mapHabitsToDate: Map<LocalDate, List<DateHabitEntity>>): Int {
    val dates = mapHabitsToDate.keys.toList().sorted()
    var currentStreak = 0
    var bestStreak = 0

    for (date in dates) {
        val habits = mapHabitsToDate[date].orEmpty()
        val isPerfect = habits.isNotEmpty() && habits.all { it.isCompleted }

        if (isPerfect) {
            currentStreak++
            bestStreak = maxOf(currentStreak, bestStreak)
        } else currentStreak = 0
    }

    return bestStreak
}

data class AchievementSection(
    val title: String,
    @DrawableRes val iconRes: Int,
    val targets: List<String>, // ["1","10","25",...]
    val progress: Int,
    val description: (target: String, index: Int) -> String
)