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
import com.example.habitstracker.habit.presentation.create_own_habit.components.all
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.presentation.components.HabitBox
import java.time.LocalDate

@Preview(showSystemUi = true)
@Composable
fun AchievementsScreenPreview(modifier: Modifier = Modifier) {
    val fakeAchievements = listOf(
        AchievementEntity(
            id = 1,
            section = "Habits Finished",
            target = 1,
            isNotified = false,
            unlockedAt = "2025-01-27"
        ),
        AchievementEntity(
            id = 2,
            section = "Habits Finished",
            target = 10,
            isNotified = false,
            unlockedAt = "2025-01-29"
        ),
        AchievementEntity(
            id = 3,
            section = "Perfect Days",
            target = 3,
            isNotified = true,
            unlockedAt = "2025-01-28"
        ),
        AchievementEntity(
            id = 4,
            section = "Best Streak",
            target = 5,
            isNotified = false,
            unlockedAt = "2025-01-30"
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screensBackgroundDark)
    ) {
        AchievementsScreen(
            mapHabitsToDate = mapOf(), // можна теж підкласти тестові дати
            allAchievements = fakeAchievements
        )
    }
}

@Composable
fun AchievementsScreen(
    mapHabitsToDate: Map<LocalDate, List<DateHabitEntity>>,
    allAchievements: List<AchievementEntity>
) {
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

    val habitsFinished =
        allAchievements.filter { it.section == stringResource(R.string.achiev_habits_finished) }
    val perfectDays =
        allAchievements.filter { it.section == stringResource(R.string.achiev_perfect_days) }
    val bestStreaks =
        allAchievements.filter { it.section == stringResource(R.string.achiev_best_streak) }

    println("AAAA " + habitsFinished.map { it.target.toString() })

    val sections = listOf(
        AchievementSection(
            title = "Habits Finished",
            iconRes = R.drawable.dart_board,
            targets = habitsFinished.map { it.target.toString() },
            progress = totalFinishedHabits,
            description = { target, index ->
                if (index == 0) "Finish Your Habit First Time"
                else "Finish Habit For The $target Times"
            }
        ),
        AchievementSection(
            title = "Perfect Days",
            iconRes = R.drawable.calendar_hexagon,
            targets = perfectDays.map { it.target.toString() },
            progress = totalPerfectDays,
            description = { target, _ -> "$target Perfect Days" }
        ),
        AchievementSection(
            title = "Best Streak",
            iconRes = R.drawable.streak_achiev,
            targets = bestStreaks.map { it.target.toString() },
            progress = bestStreak,
            description = { target, _ -> "$target Days Streak" }
        )
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