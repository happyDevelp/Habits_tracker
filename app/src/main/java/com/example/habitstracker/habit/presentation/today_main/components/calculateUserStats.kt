package com.example.habitstracker.habit.presentation.today_main.components

import com.example.habitstracker.habit.domain.ShownHabit
import com.example.habitstracker.habit.domain.toDateHabitEntity
import com.example.habitstracker.habit.presentation.today_main.utility.getBestStreak
import com.example.habitstracker.profile.domain.model.UserStats
import com.example.habitstracker.statistic.presentation.rolling30DayConsistency
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

fun calculateUserStats(
    map: Map<LocalDate, List<ShownHabit>>
): UserStats {
    if (map.isEmpty()) {
        return UserStats(
            totalCompletedHabits = 0,
            bestStreak = 0,
            currentStreak = 0,
            perfectDaysTotal = 0,
            perfectDaysThisWeek = 0,
            consistencyPercent = 0.0
        )
    }

    // all items (all date records by habit)
    val allItems = map.values.flatten()

    // 1)
    val totalCompletedHabits = allItems.count { it.isSelected }
    val totalItems = allItems.size

    // 2)
    val perfectDaysTotal = map.values.count { habits ->
        habits.isNotEmpty() && habits.all { it.isSelected }
    }

    // 3)
    val bestStreak = getBestStreak(map)

    // 4) current streak — How many consecutive perfect days to today back
    val today = LocalDate.now()
    var currentStreak = 0
    var date = today

    while (true) {
        val habits = map[date] ?: break
        if (habits.isNotEmpty() && habits.all { it.isSelected }) {
            currentStreak++
            date = date.minusDays(1)
        } else break
    }

    // 5) perfect days this week
    val weekFields = WeekFields.of(Locale.getDefault())
    val currentWeek = today.get(weekFields.weekOfWeekBasedYear())
    val currentYear = today.get(weekFields.weekBasedYear())

    val perfectDaysThisWeek = map.entries.count { (d, habits) ->
        val week = d.get(weekFields.weekOfWeekBasedYear())
        val year = d.get(weekFields.weekBasedYear())
        year == currentYear &&
                week == currentWeek &&
                habits.isNotEmpty() &&
                habits.all { it.isSelected }
    }

    val consistencyPercent =
        if (totalItems == 0) 0
        else  {
            val allDateHabits = map.flatMap { (date, habits) ->
                habits.map { it.toDateHabitEntity(date = date.toString()) }
            }
            rolling30DayConsistency(allDateHabits)
        }

    return UserStats(
        totalCompletedHabits = totalCompletedHabits,
        bestStreak = bestStreak,
        currentStreak = currentStreak,
        perfectDaysTotal = perfectDaysTotal,
        perfectDaysThisWeek = perfectDaysThisWeek,
        consistencyPercent = consistencyPercent.toDouble()
    )
}