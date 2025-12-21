package com.example.habitstracker.history.presentation.tab_screens

import android.util.Log
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
import com.example.habitstracker.core.presentation.UiText
import com.example.habitstracker.core.presentation.theme.screenBackgroundDark
import com.example.habitstracker.habit.domain.DateHabitEntity
import com.example.habitstracker.history.domain.AchievementEntity
import com.example.habitstracker.history.presentation.components.AchievementBox
import java.time.LocalDate

@Composable
fun AchievementsScreen(
    mapHabitsToDate: Map<LocalDate, List<DateHabitEntity>>,
    allAchievements: List<AchievementEntity>,
) {
    val totalFinishedHabits = mapHabitsToDate
        .values
        .flatten()
        .count { it.completed }

    val totalPerfectDays = mapHabitsToDate
        .values
        .count {
            it.all { habit -> habit.completed } // if all habits are completed
        }

    val bestStreak = getBestStreak(mapHabitsToDate)

    Log.d("AchievementsScreen", "allAchievements: $allAchievements")

    val habitsFinished =
        allAchievements.filter { it.section == "Habits Finished" }
    val perfectDays =
        allAchievements.filter { it.section == "Perfect Days" }
    val bestStreaks =
        allAchievements.filter { it.section == "Best Streak" }

    val sections = listOf(
        AchievementSection(
            title = stringResource(R.string.achiev_habits_finished),
            iconRes = R.drawable.dart_board,
            targets = habitsFinished.map { it.target.toString() },
            progress = totalFinishedHabits,
            description = { target, index ->
                UiText.StringResources(R.string.count_habits_finished, target)
            },
            isNotified = habitsFinished.map { it.notified },
            unlockedAt = habitsFinished.map { it.unlockedAt }
        ),
        AchievementSection(
            title = stringResource(R.string.achiev_perfect_days),
            iconRes = R.drawable.calendar_hexagon,
            targets = perfectDays.map { it.target.toString() },
            progress = totalPerfectDays,
            description = { target, _ ->
                UiText.StringResources(
                    R.string.target_perfect_days,
                    target
                )
            },
            isNotified = habitsFinished.map { it.notified },
            unlockedAt = habitsFinished.map { it.unlockedAt }
        ),
        AchievementSection(
            title = stringResource(R.string.achiev_best_streak),
            iconRes = R.drawable.streak_achiev,
            targets = bestStreaks.map { it.target.toString() },
            progress = bestStreak,
            description = { target, _ ->
                UiText.StringResources(
                    R.string.count_days_streak,
                    target
                )
            },
            isNotified = habitsFinished.map { it.notified },
            unlockedAt = habitsFinished.map { it.unlockedAt }
        )
    )

    LazyColumn {
        items(sections.size) { index ->
            AchievementBox(section = sections[index], allAchievements = allAchievements)
        }
    }
}

private fun getBestStreak(mapHabitsToDate: Map<LocalDate, List<DateHabitEntity>>): Int {
    val dates = mapHabitsToDate.keys.toList().sorted()
    var currentStreak = 0
    var bestStreak = 0

    for (date in dates) {
        val habits = mapHabitsToDate[date].orEmpty()
        val isPerfect = habits.isNotEmpty() && habits.all { it.completed }

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
    val description: (target: String, index: Int) -> UiText,
    val isNotified: List<Boolean>,
    val unlockedAt: List<String>
)

@Preview(showSystemUi = true)
@Composable
private fun AchievementsScreenPreview(modifier: Modifier = Modifier) {
    val fakeAchievements = listOf(
        AchievementEntity(
            id = 1,
            section = "Habits Finished",
            target = 1,
            notified = false,
            unlockedAt = "2025-01-27"
        ),
        AchievementEntity(
            id = 2,
            section = "Habits Finished",
            target = 10,
            notified = false,
            unlockedAt = "2025-01-29"
        ),
        AchievementEntity(
            id = 3,
            section = "Perfect Days",
            target = 3,
            notified = true,
            unlockedAt = "2025-01-28"
        ),
        AchievementEntity(
            id = 4,
            section = "Best Streak",
            target = 5,
            notified = false,
            unlockedAt = "2025-01-30"
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackgroundDark)
    ) {
        AchievementsScreen(
            mapHabitsToDate = mapOf(),
            allAchievements = fakeAchievements,
        )
    }
}
